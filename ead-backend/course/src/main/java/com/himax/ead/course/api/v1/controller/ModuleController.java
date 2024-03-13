package com.himax.ead.course.api.v1.controller;


import com.himax.ead.course.api.GetMessages;
import com.himax.ead.course.api.v1.mapper.modules.ModuleMapper;
import com.himax.ead.course.api.v1.model.ModuleDto;
import com.himax.ead.course.domain.exception.EntityNotFoundException;
import com.himax.ead.course.domain.model.Course;
import com.himax.ead.course.domain.model.Modules;
import com.himax.ead.course.domain.service.CourseService;
import com.himax.ead.course.domain.service.ModuleService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Log4j2
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class ModuleController {

    @Autowired
    ModuleService moduleService;

    @Autowired
    CourseService courseService;

    @Autowired
    ModuleMapper mapper;

    @PostMapping("/courses/{courseId}/modules")
    @ResponseStatus(HttpStatus.CREATED)
    public ModuleDto saveModule(@PathVariable UUID courseId,
                                @RequestBody @Valid ModuleDto moduleDto){
        log.debug("POST saveModule moduleDto received {} ", moduleDto.toString());
        Optional<Course> courseModelOptional = courseService.findById(courseId);
        if(courseModelOptional.isEmpty()){
            throw new EntityNotFoundException(GetMessages.getCourseNotExist(courseId));
        }

        Modules module = mapper.toDomain(moduleDto);
        module.setCourse(courseModelOptional.get());
        Modules moduleModel = moduleService.save(module);
        log.debug("POST saveModule moduleId saved {} ", moduleModel.getId());
        log.info("Module saved successfully moduleId {} ", moduleModel.getId());
        return mapper.toDto( moduleModel);
    }

    @DeleteMapping("/courses/{courseId}/modules/{moduleId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteModule(@PathVariable UUID courseId,
                             @PathVariable UUID moduleId){
        log.debug("DELETE deleteModule moduleId received {} ", moduleId);
        Optional<Course> courseModelOptional = courseService.findById(courseId);
        if(courseModelOptional.isEmpty()){
            throw new EntityNotFoundException(GetMessages.getCourseNotExist( courseId));
        }

        Optional<Modules> moduleModelOptional = moduleService.findById(moduleId);
        if(moduleModelOptional.isEmpty()){
            throw new EntityNotFoundException(GetMessages.getModuleNotExist(moduleId));
        }
        moduleService.delete(moduleModelOptional.get());
        log.debug("DELETE deleteModule moduleId deleted {} ", moduleId);
        log.info("Module deleted successfully moduleId {} ", moduleId);
    }

    @PutMapping("/courses/{courseId}/modules/{moduleId}")
    public ModuleDto updateModule(@PathVariable(value="courseId") UUID courseId,
                                  @PathVariable(value="moduleId") UUID moduleId,
                                  @RequestBody @Valid ModuleDto moduleDto){
        log.debug("PUT updateModule moduleDto received {} ", moduleDto.toString());
        Optional<Course> courseModelOptional = courseService.findById(courseId);
        if(courseModelOptional.isEmpty()){
            throw new EntityNotFoundException(GetMessages.getCourseNotExist( courseId));
        }

        Optional<Modules> moduleModelOptional = moduleService.findById(moduleId);
        if(moduleModelOptional.isEmpty()){
            throw new EntityNotFoundException(GetMessages.getModuleNotExist(moduleId));
        }

        Modules existing = moduleModelOptional.get();
        Modules updated = mapper.toDomain(moduleDto);
        Modules moduleModel =  moduleService.save(mapper.update(updated,existing));

        log.debug("PUT updateModule moduleId saved {} ", moduleModel.getId());
        log.info("Module updated successfully moduleId {} ", moduleModel.getId());
        return mapper.toDto(moduleModel);
    }

    @GetMapping("/courses/{courseId}/modules")
    public List<ModuleDto> getAllModules(@PathVariable(value="courseId") UUID courseId){
        Optional<Course> courseModelOptional = courseService.findById(courseId);
        if(courseModelOptional.isEmpty()){
            throw new EntityNotFoundException(GetMessages.getCourseNotExist( courseId));
        }

        return mapper.toDtoList(moduleService.findAllByCourse(courseId));
    }

    @GetMapping("/courses/{courseId}/modules/{moduleId}")
    public ModuleDto getOneModule(@PathVariable(value="courseId") UUID courseId,
                                  @PathVariable(value="moduleId") UUID moduleId){
        Optional<Course> courseModelOptional = courseService.findById(courseId);
        if(courseModelOptional.isEmpty()){
            throw new EntityNotFoundException(GetMessages.getCourseNotExist( courseId));
        }

        Optional<Modules> moduleModelOptional = moduleService.findById(moduleId);
        if(moduleModelOptional.isEmpty()){
            throw new EntityNotFoundException(GetMessages.getModuleNotExist(moduleId));
        }
        return mapper.toDto(moduleModelOptional.get());
    }
}
