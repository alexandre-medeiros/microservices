package com.himax.ead.authuser.api.v1.model.auth;

import com.himax.ead.authuser.core.validation.cpf.CPF;
import com.himax.ead.authuser.core.validation.nullsizeable.NullSizeable;
import lombok.Data;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
@Data
public class RegistrationInputDto {
    @NullSizeable(min=4, max = 50)
    private String username;

    @Email
    @NullSizeable(min=6, max = 50)
    private String email;

    @NullSizeable(min=6, max = 20)
    private String password;

    @NullSizeable(min=6, max = 250)
    private String fullName;

    @Size(max = 20)
    private String phoneNumber;

    @CPF
    private String cpf;

    @Override
    public String toString() {
        return "RegistrationInputDto{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", fullName='" + fullName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", cpf='" + cpf + '\'' +
                '}';
    }
}
