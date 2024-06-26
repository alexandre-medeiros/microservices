package com.himax.ead.authuser.api.v1.model.user;

import lombok.Data;
import java.util.UUID;

@Data
public class UserEventDto {

    private UUID id;
    private String username;
    private String email;
    private String fullName;
    private String userStatus;
    private String userType;
    private String phoneNumber;
    private String cpf;
    private String imageUrl;
    private String actionType;
}
