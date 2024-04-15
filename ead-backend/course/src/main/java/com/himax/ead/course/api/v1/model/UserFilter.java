package com.himax.ead.course.api.v1.model;

import lombok.Getter;
import java.beans.ConstructorProperties;

@Getter
public class UserFilter {

    private final String userType;
    private final String userStatus;
    private final String email;
    private final String fullName;

    @ConstructorProperties({"userType", "userStatus", "email", "fullName"})
    public UserFilter(String userType, String userStatus, String email, String fullName) {
        this.userType = userType;
        this.userStatus = userStatus;
        this.email = email;
        this.fullName = fullName;
    }
}
