package com.himax.ead.authuser.api.v1.model.auth;

import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
@Data
public class PasswordInputDto {
    @NotBlank
    @Size(min=6, max = 20)
    private String password;

    @NotBlank
    @Size(min=6, max = 20)
    private String oldPassword;
}
