package com.himax.ead.course.api.v1.mapper.modules;


import com.himax.ead.course.api.v1.model.ModuleDto;
import com.himax.ead.course.domain.model.Modules;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ModuleMapper {
    ModuleDto toDto(Modules lesson);
    Modules toDomain(ModuleDto dto);
    @Mapping(target = "id", ignore = true)
    Modules update(Modules update, @MappingTarget Modules existing);
    List<ModuleDto> toDtoList(List<Modules> list);
}
