package com.himax.ead.course.api.v1.model;


import com.himax.ead.course.domain.enums.CourseLevel;
import lombok.Getter;
import lombok.Setter;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
public class CourseUserDto {

    private UUID id;
    private String name;
    private UUID userInstructor;
    private CourseLevel courseLevel;
    private Set<UserCourseDto> users;
}
