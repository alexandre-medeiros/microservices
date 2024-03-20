package com.himax.ead.course.api.v1.controller;

import com.himax.ead.course.api.GetMessages;
import com.himax.ead.course.api.v1.mapper.course.CourseMapper;
import com.himax.ead.course.api.v1.model.CourseDto;
import com.himax.ead.course.api.v1.model.CourseFilter;
import com.himax.ead.course.domain.exception.EntityNotFoundException;
import com.himax.ead.course.domain.model.Course;
import com.himax.ead.course.domain.service.CourseService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Log4j2
@AllArgsConstructor
@RestController
@RequestMapping("/courses")
@CrossOrigin(origins = "*", maxAge = 3600)
public class CourseController {
    private CourseService courseService;
    private CourseMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CourseDto saveCourse(@RequestBody @Valid CourseDto dto){
        log.debug("POST saveCourse courseDto received {} ", dto.toString());
        Course course = mapper.toDomain(dto);
        Course saved = courseService.save(course);
        log.debug("POST saveCourse courseId saved {} ", saved.getId());
        log.info("Course saved successfully courseId {} ", saved.getId());
        return mapper.toDto(saved);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCourse(@PathVariable UUID id){
        log.debug("DELETE deleteCourse courseId received {} ", id);
        Optional<Course> courseModelOptional = courseService.findById(id);
        if(courseModelOptional.isEmpty()){
            throw new EntityNotFoundException(GetMessages.getCourseNotExist( id));
        }
        courseService.delete(courseModelOptional.get());
        log.debug("DELETE deleteCourse courseId deleted {} ", id);
        log.info("Course deleted successfully courseId {} ", id);
    }

    @PutMapping("/{id}")
    public CourseDto updateCourse(@PathVariable UUID id, @RequestBody @Valid CourseDto courseDto){
        log.debug("PUT updateCourse courseDto received {} ", courseDto.toString());
        Optional<Course> courseModelOptional = courseService.findById(id);
        if(courseModelOptional.isEmpty()){
            throw new EntityNotFoundException(GetMessages.getCourseNotExist( id));
        }

        Course existing = courseModelOptional.get();
        Course updated = mapper.toDomain(courseDto);

        Course courseModel = courseService.save(mapper.update(updated, existing));
        log.debug("PUT updateCourse courseId saved {} ", courseModel.getId());
        log.info("Course updated successfully courseId {} ", courseModel.getId());
        return mapper.toDto(courseModel);
    }

    @GetMapping
    public Page<CourseDto> getAllCourses(
            @Valid CourseFilter filter,
            @PageableDefault(page = 0, size = 10, sort = "name", direction = Sort.Direction.ASC) Pageable pageable,
            @RequestParam(required = false) UUID userId){
        List<CourseDto> list = mapper.toDtoList(courseService.findAllWithFilter(filter, userId, pageable).toList());
        return new PageImpl<>(list,pageable, list.size());
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CourseDto getOneCourse(@PathVariable UUID id){
        Optional<Course> courseModelOptional = courseService.findById(id);
        return courseModelOptional.map(course -> mapper.toDto(course))
                .orElseThrow(()->new EntityNotFoundException(GetMessages.getCourseNotExist( id)));
    }
}
