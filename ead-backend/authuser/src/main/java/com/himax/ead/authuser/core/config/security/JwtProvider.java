package com.himax.ead.authuser.core.config.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Date;
import java.util.stream.Collectors;

@Log4j2
@Component
public class JwtProvider {

    @Value("${ead.auth.jwtSecret}")
    private String jwtSecret;
    @Value("${ead.auth.jwtExpirationMs}")
    private String jwtExpirationMs;
    private Jws<Claims> claims;

    public String generateJwt(Authentication auth) {
        UserDetailsImpl user = (UserDetailsImpl) auth.getPrincipal();
        Date date = new Date(System.currentTimeMillis());
        String roles = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        return Jwts.builder()
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .setSubject(user.getId().toString())
                .claim("roles", roles)
                .setIssuedAt(date)
                .setExpiration(new Date(date.getTime() + getJwtExpirationMs()))
                .compact();
    }

    public String getSubject() {
        return extractAllClaims().getSubject();
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

    private Integer getJwtExpirationMs() {
        String[] factors = jwtExpirationMs.split("\\*");
        int sec = Integer.parseInt(factors[0]);
        int min = Integer.parseInt(factors[1]);
        int hour = Integer.parseInt(factors[2]);
        return sec * min * hour * 1000;
    }
}
