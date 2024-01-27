package com.himax.ead.authuser.api.v1.model.user;

import lombok.Data;
import javax.validation.constraints.NotBlank;
@Data
public class ImageInputDto {
    @NotBlank
    private String imageUrl;
}
