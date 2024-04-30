package com.himax.ead.course.api.v1.model;

import lombok.Getter;
import lombok.Setter;
import java.util.UUID;

@Getter
@Setter
public class NotificationCommandDto {

    private String title;
    private String message;
    private UUID userId;
}
