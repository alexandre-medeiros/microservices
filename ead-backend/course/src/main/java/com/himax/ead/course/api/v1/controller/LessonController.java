package com.himax.ead.course.api.v1.controller;

import com.himax.ead.course.api.v1.mapper.lesson.LessonMapper;
import com.himax.ead.course.api.v1.model.LessonDto;
import com.himax.ead.course.domain.model.Lesson;
import com.himax.ead.course.domain.service.LessonService;
import com.himax.ead.course.domain.service.ModuleService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("hasAnyRole('INSTRUCTOR')")
    @PostMapping("/modules/{moduleId}/lessons")
    @ResponseStatus(HttpStatus.CREATED)
    public LessonDto saveLesson(@PathVariable UUID moduleId,
                                @RequestBody @Valid LessonDto lessonDto) {
        log.debug("POST saveLesson lessonDto received {} ", lessonDto.toString());
        Lesson lesson = mapper.toDomain(lessonDto);
        lesson.setModule(moduleService.findById(moduleId));
        Lesson lessonModel = lessonService.save(lesson);

        log.debug("POST saveLesson lessonId saved {} ", lessonModel.getId());
        log.info("Lesson saved successfully lessonId {} ", lessonModel.getId());
        return mapper.toDto(lessonModel);
    }

    @PreAuthorize("hasAnyRole('INSTRUCTOR')")
    @DeleteMapping("/modules/{moduleId}/lessons/{lessonId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLesson(@PathVariable UUID moduleId,
                             @PathVariable UUID lessonId) {
        log.debug("DELETE deleteLesson lessonId received {} ", lessonId);
        moduleService.findById(moduleId);
        lessonService.delete(lessonService.findLessonIntoModule(moduleId, lessonId));
        log.debug("DELETE deleteLesson lessonId deleted {} ", lessonId);
        log.info("Lesson deleted successfully lessonId {} ", lessonId);
    }

    @PreAuthorize("hasAnyRole('INSTRUCTOR')")
    @PutMapping("/modules/{moduleId}/lessons/{lessonId}")
    public LessonDto updateLesson(@PathVariable(value = "moduleId") UUID moduleId,
                                  @PathVariable(value = "lessonId") UUID lessonId,
                                  @RequestBody @Valid LessonDto lessonDto) {
        log.debug("PUT updateLesson lessonDto received {} ", lessonDto.toString());
        moduleService.findById(moduleId);
        Lesson existing = lessonService.findLessonIntoModule(moduleId, lessonId);
        Lesson updated = mapper.toDomain(lessonDto);

        Lesson lesson = mapper.update(updated, existing);
        Lesson lessonModel = lessonService.save(lesson);

        log.debug("PUT updateLesson lessonId saved {} ", lessonModel.getId());
        log.info("Lesson updated successfully lessonId {} ", lessonModel.getId());
        return mapper.toDto(lessonModel);
    }

    @PreAuthorize("hasAnyRole('STUDENT')")
    @GetMapping("/modules/{moduleId}/lessons")
    public List<LessonDto> getAllLessons(@PathVariable(value = "moduleId") UUID moduleId) {
        moduleService.findById(moduleId);
        return mapper.toDtoList(lessonService.findAllByModule(moduleId));
    }

    @PreAuthorize("hasAnyRole('STUDENT')")
    @GetMapping("/modules/{moduleId}/lessons/{lessonId}")
    public LessonDto getOneLesson(@PathVariable UUID moduleId,
                                  @PathVariable UUID lessonId) {
        return mapper.toDto(lessonService.findLessonIntoModule(moduleId, lessonId));
    }
}
