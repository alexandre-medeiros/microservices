package com.himax.ead.course.api.v1.model;

import com.himax.ead.course.domain.model.Users;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import java.util.UUID;

@Data
public class UserEventDto {

    private UUID id;
    private String email;
    private String fullName;
    private String userStatus;
    private String userType;
    private String cpf;
    private String imageUrl;
    private String actionType;

    public Users toModel() {
        Users user = new Users();
        BeanUtils.copyProperties(this, user);
        return user;
    }
}
