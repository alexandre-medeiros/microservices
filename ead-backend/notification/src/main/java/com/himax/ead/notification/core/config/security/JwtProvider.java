package com.himax.ead.notification.core.config.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.security.Key;

@Log4j2
@Component
public class JwtProvider {

    @Value("${ead.auth.jwtSecret}")
    private String jwtSecret;
    private Jws<Claims> claims;

    public String getSubject() {
        return extractAllClaims().getSubject();
    }

    public String getClaims(String claim) {
        return extractAllClaims().get(claim, String.class);
    }

    private Claims extractAllClaims() {
        return this.claims
                .getBody();
    }

    public boolean isTokenValid(String token) {
        if (token == null) {
            return false;
        }

        try {
            this.claims = parseClaims(token);
            return true;
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            log.error("JWT token is malformed: {}", e.getMessage());
        } catch (SignatureException e) {
            log.error("Invalid JWT token signature: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims is empty: {}", e.getMessage());
        }
        return false;
    }

    private Jws<Claims> parseClaims(String token) throws JwtException {
        return Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token);
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
