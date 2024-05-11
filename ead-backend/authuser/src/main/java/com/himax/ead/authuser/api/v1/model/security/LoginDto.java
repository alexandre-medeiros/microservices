package com.himax.ead.authuser.api.v1.model.security;

import lombok.Data;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import javax.validation.constraints.NotBlank;

@Data
public class LoginDto {

    @NotBlank
    private String username;
    @NotBlank
    private String password;

    public Authentication toAuthentication() {
        return new UsernamePasswordAuthenticationToken(this.username, this.password);
    }
}
