package com.himax.ead.course.api.v1.model;

import com.himax.ead.course.domain.enums.UserStatus;
import com.himax.ead.course.domain.enums.UserType;
import lombok.Data;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
public class UserDto {
    private UUID id;
    private String username;
    private String email;
    private String fullName;
    private UserStatus userStatus;
    private UserType userType;
    private String phoneNumber;
    private String cpf;
    private String imageUrl;
    private OffsetDateTime creationDate;
    private OffsetDateTime lastUpdateDate;
}
