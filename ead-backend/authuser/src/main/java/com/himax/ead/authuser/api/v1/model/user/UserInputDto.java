package com.himax.ead.authuser.api.v1.model.user;

import com.himax.ead.authuser.core.validation.cpf.CPF;
import lombok.Data;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
@Data
public class UserInputDto {
    @NotBlank
    @Email
    @Size(max = 50)
    private String email;
    @NotBlank
    @Size(max = 150)
    private String fullName;
    @Size(max = 20)
    private String phoneNumber;
    @CPF
    private String cpf;
}
