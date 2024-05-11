package com.himax.ead.authuser.core.config.security;

import com.himax.ead.authuser.domain.model.Users;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.UUID;

@Data
@Builder(setterPrefix = "with")
public class UserDetailsImpl implements UserDetails {

    private UUID id;
    private String username;
    private String password;
    private String fullName;
    private String email;
    private Collection<? extends GrantedAuthority> authorities;


    public static UserDetailsImpl build(Users user) {
        return UserDetailsImpl.builder()
                .withId(user.getId())
                .withUsername(user.getUsername())
                .withPassword(user.getPassword())
                .withFullName(user.getFullName())
                .withEmail(user.getEmail())
                .withAuthorities(user.getRoles())
                .build();
    }

    public boolean isNotTheSameUser(UUID id) {
        return !this.id.equals(id);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
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
}
