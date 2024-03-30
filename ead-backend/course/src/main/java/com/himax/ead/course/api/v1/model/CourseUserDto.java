package com.himax.ead.course.api.v1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@AllArgsConstructor
public class CourseUserDto {
    private final UUID id;
    @NotNull
    private final UUID userId;
    private CourseDto course;
}
