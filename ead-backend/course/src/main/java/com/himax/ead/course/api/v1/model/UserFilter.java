package com.himax.ead.authuser.api.v1.model.user;

import com.himax.ead.authuser.domain.enums.UserStatus;
import com.himax.ead.authuser.domain.enums.UserType;
import lombok.Getter;
import java.beans.ConstructorProperties;

@Getter
public class UserFilter {
    private final UserType userType;
    private final UserStatus userStatus;
    private final String email;
    private final String fullName;

    @ConstructorProperties({"userType","userStatus", "email", "fullName"})
    public UserFilter(UserType userType, UserStatus userStatus, String email, String fullName) {
        this.userType = userType;
        this.userStatus = userStatus;
        this.email = email;
        this.fullName = fullName;
    }
}
