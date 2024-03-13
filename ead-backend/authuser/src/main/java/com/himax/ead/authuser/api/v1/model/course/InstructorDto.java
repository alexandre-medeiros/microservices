package com.himax.ead.authuser.api.v1.model.course;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
public class InstructorDto {

    @NotNull
    private UUID userId;
}
