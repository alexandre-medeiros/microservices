package com.himax.ead.course.api.v1.model;


import com.himax.ead.course.domain.enums.CourseLevel;
import com.himax.ead.course.domain.enums.CourseStatus;
import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Setter
public class CourseDto {
    private UUID id;
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    private String imageUrl;
    @NotNull
    private CourseStatus courseStatus;
    @NotNull
    private UUID userInstructor;
    @NotNull
    private CourseLevel courseLevel;
}
