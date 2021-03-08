package xyz.thefrontpage.rest.resource;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.thefrontpage.dto.AuthResponse;
import xyz.thefrontpage.dto.LoginInput;
import xyz.thefrontpage.dto.RefreshTokenInput;
import xyz.thefrontpage.dto.RegisterInput;
import xyz.thefrontpage.service.AuthService;
import xyz.thefrontpage.service.RefreshTokenService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthResource {

    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/register")
    public ResponseEntity<String> signUp(@RequestBody RegisterInput registerInput) {
        authService.register(registerInput);
        return new ResponseEntity<>("User registration was successful", HttpStatus.OK);
    }

    @GetMapping("/confirm")
    public ResponseEntity<String> confirmToken(@RequestParam("token") String token) {
        authService.confirmToken(token);
        return new ResponseEntity<>("Account activated successfully", HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginInput loginInput) {
        AuthResponse authResponse = authService.login(loginInput);
        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }

    @PostMapping("/refresh/token")
    public ResponseEntity<AuthResponse> refreshTokens(@Valid @RequestBody RefreshTokenInput refreshTokenInput) {
        return ResponseEntity.status(HttpStatus.OK).body(authService.refreshToken(refreshTokenInput));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@Valid @RequestBody RefreshTokenInput refreshTokenInput) {
        refreshTokenService.deleteRefreshToken(refreshTokenInput.getRefreshToken());
        return ResponseEntity.status(HttpStatus.OK).body("Refresh token deleted successfully!");
    }

}
