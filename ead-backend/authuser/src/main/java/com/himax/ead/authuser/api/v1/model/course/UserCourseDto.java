package com.himax.ead.authuser.api.v1.model.course;

import com.himax.ead.authuser.api.v1.model.user.UserOutputDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@AllArgsConstructor
public class UserCourseDto {
    private UUID id;
    @NotNull
    private UUID courseId;
    private UserOutputDto user;
}

