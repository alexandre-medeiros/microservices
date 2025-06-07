package com.himax.ead.notification.core.config.security;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
public class UserDetailsImpl implements UserDetails {

    private UUID id;
    private Collection<? extends GrantedAuthority> authorities;

    public static UserDetailsImplBuilder builder() {
        return new UserDetailsImplBuilder();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public static class UserDetailsImplBuilder {

        private final UserDetailsImpl userDetails;

        public UserDetailsImplBuilder() {
            this.userDetails = new UserDetailsImpl();
        }

        public UserDetailsImplBuilder withId(UUID id) {
            userDetails.setId(id);
            return this;
        }

        public UserDetailsImplBuilder withAuthorities(String authorities) {
            userDetails.setAuthorities(generateGrantedAuthorityList(authorities));
            return this;
        }

        public UserDetailsImpl build() {
            return this.userDetails;
        }

        private Collection<? extends GrantedAuthority> generateGrantedAuthorityList(String strAuth) {
            return Arrays.stream(strAuth.split(","))
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
        }
    }
}
