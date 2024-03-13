package com.himax.ead.authuser.api.v1.model.security;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginDto {

    @NotBlank
    private String username;
    @NotBlank
    private String password;

}
