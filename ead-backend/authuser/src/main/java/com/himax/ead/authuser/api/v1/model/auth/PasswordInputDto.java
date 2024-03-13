package com.himax.ead.authuser.api.v1.model.auth;

import com.himax.ead.authuser.core.validation.nullsizeable.NullSizeable;
import lombok.Data;
@Data
public class PasswordInputDto {
    @NullSizeable(min=6, max = 20)
    private String password;

    @NullSizeable(min=6, max = 20)
    private String oldPassword;
}
