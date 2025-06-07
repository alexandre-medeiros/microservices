package com.himax.ead.notification.core.config.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationCurrentUserService {

    public UserDetailsImpl getCurrentUser() {
        Authentication authentication = getAuthentication();
        return (UserDetailsImpl) authentication.getPrincipal();
    }

    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
