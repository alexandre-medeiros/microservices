package com.himax.ead.course.api.v1.model;

import lombok.Getter;
import lombok.Setter;
import java.util.UUID;

@Getter
@Setter
public class UserCourseDto {

    private UUID id;
    private String email;
    private String fullName;
}
