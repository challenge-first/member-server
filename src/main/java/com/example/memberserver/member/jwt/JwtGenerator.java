package com.example.memberserver.member.jwt;

import com.example.memberserver.member.role.Role;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Slf4j
@Component
public class JwtGenerator {
    private static final String TOKEN_TYPE = "Bearer ";
    private final Key secretKey;
    private final long TOKEN_EXPIRES_IN;
    private final String AUTHORIZATION_KEY = "auth";

    public static final String HEADER_NAME = "Authorization";

    public JwtGenerator(@Value("${jwt.secret.key}") final String secretKey,
        @Value("${jwt.expire.length}") final long tokenExpiresIn) {
        this.secretKey = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        this.TOKEN_EXPIRES_IN = tokenExpiresIn;
    }

    public String createAccessToken(final Long id, final Role role) {
        final Date now = new Date();
        final Date validity = new Date(now.getTime() + TOKEN_EXPIRES_IN);
        return TOKEN_TYPE + Jwts.builder()
            .setSubject(id.toString())
            .setIssuedAt(now)
            .setExpiration(validity)
            .claim(AUTHORIZATION_KEY, role)
            .signWith(secretKey, SignatureAlgorithm.HS256)
            .compact();
    }
}
