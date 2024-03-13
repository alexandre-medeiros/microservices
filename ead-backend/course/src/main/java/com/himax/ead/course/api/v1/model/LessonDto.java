package com.himax.ead.course.api.v1.model;


import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Data
public class LessonDto {
    private UUID id;
    @NotBlank
    private String title;
    private String description;
    @NotBlank
    private String videoUrl;
}
