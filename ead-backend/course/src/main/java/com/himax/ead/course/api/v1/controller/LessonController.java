package com.himax.ead.course.api.v1.controller;

import com.himax.ead.course.api.GetMessages;
import com.himax.ead.course.api.v1.mapper.lesson.LessonMapper;
import com.himax.ead.course.api.v1.model.LessonDto;
import com.himax.ead.course.domain.exception.EntityNotFoundException;
import com.himax.ead.course.domain.model.Lesson;
import com.himax.ead.course.domain.model.Modules;
import com.himax.ead.course.domain.service.LessonService;
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
public class LessonController {
    @Autowired
    LessonService lessonService;

    @Autowired
    ModuleService moduleService;

    @Autowired
    LessonMapper mapper;

    @PostMapping("/modules/{moduleId}/lessons")
    @ResponseStatus(HttpStatus.CREATED)
    public LessonDto saveLesson(@PathVariable UUID moduleId,
                                @RequestBody @Valid LessonDto lessonDto){
        log.debug("POST saveLesson lessonDto received {} ", lessonDto.toString());
        Optional<Modules> moduleModelOptional = moduleService.findById(moduleId);
        if(moduleModelOptional.isEmpty()){
            throw new EntityNotFoundException(GetMessages.getModuleNotExist(moduleId));
        }

        Lesson lesson = mapper.toDomain(lessonDto);
        lesson.setModule(moduleModelOptional.get());
        Lesson lessonModel = lessonService.save(lesson);

        log.debug("POST saveLesson lessonId saved {} ", lessonModel.getId());
        log.info("Lesson saved successfully lessonId {} ", lessonModel.getId());
        return mapper.toDto(lessonModel);
    }

    @DeleteMapping("/modules/{moduleId}/lessons/{lessonId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLesson(@PathVariable UUID moduleId,
                             @PathVariable UUID lessonId){
        log.debug("DELETE deleteLesson lessonId received {} ", lessonId);
        Optional<Modules> moduleModelOptional = moduleService.findById(moduleId);
        if(moduleModelOptional.isEmpty()){
            throw new EntityNotFoundException(GetMessages.getModuleNotExist(moduleId));
        }

        Optional<Lesson> lessonModelOptional = lessonService.findLessonIntoModule(moduleId, lessonId);
        if(lessonModelOptional.isEmpty()){
            throw new EntityNotFoundException(GetMessages.getLessonNotExist(lessonId));
        }

        lessonService.delete(lessonModelOptional.get());
        log.debug("DELETE deleteLesson lessonId deleted {} ", lessonId);
        log.info("Lesson deleted successfully lessonId {} ", lessonId);
    }

    @PutMapping("/modules/{moduleId}/lessons/{lessonId}")
    public LessonDto updateLesson(@PathVariable(value="moduleId") UUID moduleId,
                                               @PathVariable(value="lessonId") UUID lessonId,
                                               @RequestBody @Valid LessonDto lessonDto){
        log.debug("PUT updateLesson lessonDto received {} ", lessonDto.toString());
        Optional<Modules> moduleModelOptional = moduleService.findById(moduleId);
        if(moduleModelOptional.isEmpty()){
            throw new EntityNotFoundException(GetMessages.getModuleNotExist(moduleId));
        }

        Optional<Lesson> lessonModelOptional = lessonService.findLessonIntoModule(moduleId, lessonId);
        if(lessonModelOptional.isEmpty()){
            throw new EntityNotFoundException(GetMessages.getLessonNotExist(lessonId));
        }
        Lesson existing = lessonModelOptional.get();
        Lesson updated =  mapper.toDomain(lessonDto);

        Lesson lesson = mapper.update(updated, existing);
        Lesson lessonModel = lessonService.save(lesson);

        log.debug("PUT updateLesson lessonId saved {} ", lessonModel.getId());
        log.info("Lesson updated successfully lessonId {} ", lessonModel.getId());
        return mapper.toDto(lessonModel);
    }

    @GetMapping("/modules/{moduleId}/lessons")
    public List<LessonDto> getAllLessons(@PathVariable(value="moduleId") UUID moduleId){
        Optional<Modules> moduleOptional = moduleService.findById(moduleId);
        if(moduleOptional.isEmpty()){
            throw new EntityNotFoundException(GetMessages.getModuleNotExist(moduleId));
        }

        return  mapper.toDtoList(lessonService.findAllByModule(moduleId));
    }

    @GetMapping("/modules/{moduleId}/lessons/{lessonId}")
    public LessonDto getOneLesson(@PathVariable UUID moduleId,
                                   @PathVariable UUID lessonId){
        Optional<Modules> moduleOptional = moduleService.findById(moduleId);
        if(moduleOptional.isEmpty()){
            throw new EntityNotFoundException(GetMessages.getLessonNotExist(lessonId));
        }

        Optional<Lesson> lessonModelOptional = lessonService.findLessonIntoModule(moduleId, lessonId);
        if(lessonModelOptional.isEmpty()){
            throw new EntityNotFoundException(GetMessages.getLessonNotExist(lessonId));
        }

        return mapper.toDto(lessonModelOptional.get());
    }
}
