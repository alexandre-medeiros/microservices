package com.himax.ead.course.api.v1.model;

import lombok.Getter;
import lombok.Setter;
import java.util.UUID;

@Getter
@Setter
public class UserDto {

    private UUID id;
    private String email;
    private String fullName;
    private String userStatus;
    private String userType;
    private String cpf;
    private String imageUrl;
}
