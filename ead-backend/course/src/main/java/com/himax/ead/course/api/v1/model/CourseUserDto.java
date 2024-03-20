package com.himax.ead.course.api.v1.model;

import lombok.Data;

import java.util.UUID;

@Data
public class CourseUserDto {

    private UUID courseId;
    private UUID userId;
}
