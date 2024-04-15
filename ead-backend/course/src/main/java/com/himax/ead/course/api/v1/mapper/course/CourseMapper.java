package com.himax.ead.course.api.v1.mapper.course;

import com.himax.ead.course.api.v1.model.CourseDto;
import com.himax.ead.course.api.v1.model.CourseUserDto;
import com.himax.ead.course.domain.model.Course;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CourseMapper {

    CourseDto toDto(Course course);

    Course toDomain(CourseDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Course update(Course update, @MappingTarget Course existing);

    List<CourseDto> toDtoList(List<Course> list);

    CourseUserDto toCourseUserDto(Course course);
}
