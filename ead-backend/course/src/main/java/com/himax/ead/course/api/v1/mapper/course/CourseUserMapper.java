package com.himax.ead.course.api.v1.mapper.course;

import com.himax.ead.course.api.v1.model.CourseUserDto;
import com.himax.ead.course.domain.model.CourseUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CourseUserMapper {
    @Mapping(target = "courseId", source = "id")
    CourseUserDto toDto(CourseUser course);
}
