package com.himax.ead.authuser.api.v1.model.auth;

import com.himax.ead.authuser.core.validation.cpf.CPF;
import lombok.Data;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
@Data
public class RegistrationInputDto {
    @NotBlank
    @Size(max = 50)
    private String username;

    @NotBlank
    @Email
    @Size(max = 50)
    private String email;

    @NotBlank
    @Size(min=6, max = 20)
    private String password;

    @NotBlank
    @Size(max = 150)
    private String fullName;

    @Size(max = 20)
    private String phoneNumber;

    @CPF
    private String cpf;
}
