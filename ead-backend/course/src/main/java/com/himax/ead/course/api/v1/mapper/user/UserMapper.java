package com.himax.ead.authuser.api.v1.mapper;

import com.himax.ead.authuser.api.v1.model.auth.PasswordInputDto;
import com.himax.ead.authuser.api.v1.model.auth.RegistrationInputDto;
import com.himax.ead.authuser.api.v1.model.user.ImageInputDto;
import com.himax.ead.authuser.api.v1.model.user.UserInputDto;
import com.himax.ead.authuser.api.v1.model.user.UserOutputDto;
import com.himax.ead.authuser.domain.model.Users;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.springframework.data.domain.Page;
import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    UserOutputDto toDto(Users user);

    Users toDomain(RegistrationInputDto dto);

    Users toDomain(UserInputDto dto);
    List<UserOutputDto> toListDto(Page<Users> users);

    Users updateUser(UserInputDto user, @MappingTarget Users existingUser);

    Users updatePassord(PasswordInputDto password, @MappingTarget Users existingUser);

    Users updateImage(ImageInputDto image, @MappingTarget Users existingUser);

}
