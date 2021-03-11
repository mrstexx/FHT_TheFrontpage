package xyz.thefrontpage.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;
import java.time.Instant;
import java.util.Date;

@Slf4j
@Service
public class JwtProvider {

    private KeyStore keyStore;

    @Value("${jwt.expiration.time}")
    private Long jwtExpirationInMillis;

    @Value("${security.jsk-key}")
    private String secureKey;

    @Value("{security.jks-store}")
    private String keyStoreName;

    @PostConstruct
    public void init() {
        InputStream resource = null;
        try {
            keyStore = KeyStore.getInstance("JKS");
            char[] storePwd = secureKey.toCharArray();
            resource = getClass().getResourceAsStream("/" + keyStoreName + ".jks");
            keyStore.load(resource, storePwd);
        } catch (IOException | CertificateException | NoSuchAlgorithmException | KeyStoreException e) {
            throw new IllegalStateException("Exception occurred while loading key store");
        } finally {
            if (resource != null) {
                try {
                    resource.close();
                } catch (IOException e) {
                    log.error("Resource closing failed: {}", e.getLocalizedMessage());
                    e.printStackTrace();
                }
            }
        }
    }

    public String generateToken(Authentication authentication) {
        User principal = (User) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject(principal.getUsername())
                .signWith(getPrivateKey())
                .setExpiration(Date.from(Instant.now().plusMillis(jwtExpirationInMillis)))
                .compact();
    }

    public String generateTokenWithUsername(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(Date.from(Instant.now()))
                .signWith(getPrivateKey())
                .setExpiration(Date.from(Instant.now().plusMillis(jwtExpirationInMillis)))
                .compact();
    }

    private PrivateKey getPrivateKey() {
        try {
            return (PrivateKey) keyStore.getKey(keyStoreName, secureKey.toCharArray());
        } catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException e) {
            throw new IllegalStateException("Exception occurred while retrieving private ket from key store");
        }
    }

    private PublicKey getPublicKey() {
        try {
            return keyStore.getCertificate(keyStoreName).getPublicKey();
        } catch (KeyStoreException e) {
            throw new IllegalStateException("Exception occurred while retrieving public ket from key store");
        }
    }

    public boolean validateToken(String jwt) {
        Jwts.parserBuilder().setSigningKey(getPublicKey()).build().parseClaimsJws(jwt);
        return true;
    }

    public String getUsernameFromJwt(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getPublicKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public Long getJwtExpirationInMillis() {
        return jwtExpirationInMillis;
    }

}
