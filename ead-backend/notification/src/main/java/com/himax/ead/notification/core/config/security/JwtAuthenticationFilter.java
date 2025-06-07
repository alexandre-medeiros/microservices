package com.himax.ead.notification.core.config.security;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@Log4j2
@AllArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private JwtProvider jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String authHeader = request.getHeader("Authorization");

            if (isHeaderValid(authHeader)) {
                String token = authHeader.substring(7);

                if (jwtService.isTokenValid(token)) {
                    UUID userId = UUID.fromString(jwtService.getSubject());
                    String roles = jwtService.getClaims("roles");
                    UserDetails user = UserDetailsImpl.builder()
                            .withId(userId)
                            .withAuthorities(roles)
                            .build();
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        } catch (Exception e) {
            log.error("Cannot set User Authentication: {}", e);
        }

        filterChain.doFilter(request, response);
    }

    private boolean isHeaderValid(String authHeader) {
        return authHeader != null && authHeader.startsWith("Bearer ");
    }
}
