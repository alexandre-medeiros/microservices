package com.himax.ead.course.api.v1.mapper.user;

import com.himax.ead.course.api.v1.model.UserDto;
import com.himax.ead.course.domain.model.Users;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    List<UserDto> toListDto(List<Users> users);
}
