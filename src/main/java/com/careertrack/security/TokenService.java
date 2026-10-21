package com.careertrack.security;

import com.careertrack.entity.User;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class TokenService {
    private final JwtEncoder jwtEncoder;
    private final JwtProperties jwtProperties;

    public TokenService(JwtEncoder jwtEncoder, JwtProperties jwtProperties) {
        this.jwtEncoder = jwtEncoder;
        this.jwtProperties = jwtProperties;
    }

    public String generateToken(User user) {
        Instant now = Instant.now();

        // Il contenuto del token (il "payload")
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("careertrack")                              // chi ha emesso il token
                .issuedAt(now)                                      // quando
                .expiresAt(now.plus(jwtProperties.expiration()))    // quando scade
                .subject(String.valueOf(user.getId()))              // CHI è l'utente
                .claim("email", user.getEmail())
                .claim("name", user.getName())
                .build();

        // Quale algoritmo usare per la firma
        JwsHeader header = JwsHeader.with(MacAlgorithm.HS256).build();

        return jwtEncoder.encode(JwtEncoderParameters.from(header, claims)).getTokenValue();
    }

    public long getExpirationSeconds() {
        return jwtProperties.expiration().toSeconds();
    }
}
