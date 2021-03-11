package xyz.thefrontpage.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.thefrontpage.domain.User;
import xyz.thefrontpage.domain.UserRole;
import xyz.thefrontpage.domain.token.ConfirmationToken;
import xyz.thefrontpage.dto.*;
import xyz.thefrontpage.repository.ConfirmationTokenRepository;
import xyz.thefrontpage.repository.UserRepository;
import xyz.thefrontpage.util.JwtProvider;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RefreshTokenService refreshTokenService;
    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final MailService mailService;

    @Value("${jwt.expiration.confirmation}")
    private Long jwtExpirationInMinutes;

    @Transactional
    public void register(RegisterInput registerInput) {
        boolean isUsernameTaken = userRepository.findByUsername(registerInput.getUsername()).isPresent();
        if (isUsernameTaken) {
            throw new IllegalStateException("Username already taken");
        }
        User user = new User(registerInput.getUsername(),
                registerInput.getEmail(),
                passwordEncoder.encode(registerInput.getPassword()),
                UserRole.USER);
        userRepository.save(user);
        String token = generateVerificationToken(user);
        // TODO hardcoded URL, REST vs. GraphQL
        mailService.sendMail(new NotificationMail(
                "User Account Activation",
                user.getEmail(),
                "http://localhost:8080/api/auth/confirm?token=" + token));

    }

    public AuthResponse login(LoginInput loginInput) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginInput.getUsername(), loginInput.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtProvider.generateToken(authentication);
        return AuthResponse.builder()
                .authToken(token)
                .refreshToken(refreshTokenService.generateRefreshToken().getToken())
                .username(loginInput.getUsername())
                .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
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

    public AuthResponse refreshToken(RefreshTokenInput refreshTokenInput) {
        refreshTokenService.validateRefreshToken(refreshTokenInput.getRefreshToken());
        String token = jwtProvider.generateTokenWithUsername(refreshTokenInput.getUsername());
        return AuthResponse.builder()
                .authToken(token)
                .refreshToken(refreshTokenInput.getRefreshToken())
                .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
                .username(refreshTokenInput.getUsername())
                .build();
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
