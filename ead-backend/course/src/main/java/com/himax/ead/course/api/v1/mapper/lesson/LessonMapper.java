package com.himax.ead.course.api.v1.mapper.lesson;


import com.himax.ead.course.api.v1.model.LessonDto;
import com.himax.ead.course.domain.model.Lesson;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface LessonMapper {
    LessonDto toDto(Lesson lesson);
    Lesson toDomain(LessonDto dto);
    @Mapping(target = "id", ignore = true)
    Lesson update(Lesson update, @MappingTarget Lesson existing);
    List<LessonDto> toDtoList(List<Lesson> list);
}
