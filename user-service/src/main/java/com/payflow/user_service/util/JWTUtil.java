package com.payflow.user_service.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.Map;

@Component
public class JWTUtil {
    private static final String SECRET = "mvovsq7JdzHMQP3OJOVRmS/aqFNesKnJ/rjKLpl9RCo=";
    private static final Logger log = LoggerFactory.getLogger(JWTUtil.class);

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    public String extractEmail(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateToken(String token) {
        try{
            extractEmail(token);
            return true;
        }catch (Exception e){
            log.error("Invalid token {}", e.getMessage());
            return false;
        }
    }

    public String extractUsername(String token) {
        return extractEmail(token);
    }

    public String generateToken(Map<String, Object> claims, String email) {
        return Jwts.builder()
                .addClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000))
                .setSubject(email)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String getRole(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token).getBody().get("role").toString();
    }
}
