package com.himax.ead.authuser.api.v1.mapper;

import com.himax.ead.authuser.api.v1.model.course.UserCourseDto;
import com.himax.ead.authuser.domain.model.UserCourse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserCourseMapper {
    UserCourseDto toDto(UserCourse course);
}
