package com.himax.ead.authuser.api.v1.model.course;

import com.himax.ead.authuser.domain.enums.CourseLevel;
import com.himax.ead.authuser.domain.enums.CourseStatus;
import lombok.Data;
import java.util.UUID;

@Data
public class CourseDto {

    private UUID id;
    private String name;
    private String description;
    private String imageUrl;
    private CourseStatus courseStatus;
    private UUID userInstructor;
    private CourseLevel courseLevel;

}
