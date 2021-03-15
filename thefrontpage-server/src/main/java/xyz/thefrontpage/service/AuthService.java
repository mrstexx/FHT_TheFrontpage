package xyz.thefrontpage.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.thefrontpage.dto.request.LoginRequest;
import xyz.thefrontpage.dto.request.RegisterRequest;
import xyz.thefrontpage.entity.User;
import xyz.thefrontpage.entity.UserRole;
import xyz.thefrontpage.entity.token.ConfirmationToken;
import xyz.thefrontpage.dto.*;
import xyz.thefrontpage.repository.ConfirmationTokenRepository;
import xyz.thefrontpage.repository.UserRepository;
import xyz.thefrontpage.util.JwtUtil;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final MailService mailService;

    @Value("${jwt.expiration.confirmation}")
    private Integer jwtExpirationInMinutes;

    @Transactional
    public void register(RegisterRequest registerRequest) {
        boolean isUsernameTaken = userRepository.findByUsername(registerRequest.getUsername()).isPresent();
        if (isUsernameTaken) {
            throw new IllegalStateException("Username already taken");
        }
        User user = new User(registerRequest.getUsername(),
                registerRequest.getEmail(),
                passwordEncoder.encode(registerRequest.getPassword()),
                UserRole.USER);
        userRepository.save(user);
        String token = generateVerificationToken(user);
        // TODO hardcoded URL, REST vs. GraphQL
        mailService.sendMail(new MailDto(
                "User Account Activation",
                user.getEmail(),
                "http://localhost:8080/api/auth/confirm?token=" + token));
    }

    public Auth login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtUtil.generateToken(authentication);
        return Auth.builder()
                .authToken(token)
                .username(loginRequest.getUsername())
                .expiresAt(Instant.now().plusMillis(jwtUtil.getJwtExpirationInMillis()))
                .build();
    }

    @Transactional
    public void confirmToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenRepository.findByToken(token)
                .orElseThrow(() -> new IllegalStateException("Token not found"));
        if (confirmationToken.getConfirmedAt() != null) {
            throw new IllegalStateException("Email already confirmed");
        }
        LocalDateTime expiresAt = confirmationToken.getExpiresAt();
        if (expiresAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("Token expired");
        }
        confirmationTokenRepository.updateConfirmedAt(token, LocalDateTime.now());
        User user = userRepository.findByUsername(confirmationToken.getUser().getUsername())
                .orElseThrow(() -> new IllegalStateException("User not found"));
        user.setEnabled(true);
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public User getCurrentUser() {
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User)
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findByUsername(principal.getUsername())
                .orElseThrow(() -> new IllegalStateException("Username not found - " + principal.getUsername()));
    }

    public void logout(String token) {
        // TODO
    }

    private String generateVerificationToken(User user) {
        String verificationToken = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(
                verificationToken,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(jwtExpirationInMinutes),
                user
        );
        confirmationTokenRepository.save(confirmationToken);
        return verificationToken;
    }

}
