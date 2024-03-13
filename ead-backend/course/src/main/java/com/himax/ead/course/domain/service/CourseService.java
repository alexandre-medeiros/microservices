package com.himax.ead.course.domain.service;

import com.himax.ead.course.domain.model.Course;
import com.himax.ead.course.domain.repository.CourseRepository;
import com.himax.ead.course.domain.repository.LessonRepository;
import com.himax.ead.course.domain.repository.ModuleRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Service
public class CourseService {
    private CourseRepository courseRepository;
    private ModuleRepository moduleRepository;
    private LessonRepository lessonRepository;

    @Transactional
    public void delete(Course course) {
        courseRepository.delete(course);
    }

    @Transactional
    public Course save(Course course) {
        return courseRepository.save(course);
    }

    public Optional<Course> findById(UUID courseId) {
        return courseRepository.findById(courseId);
    }

    public Page<Course> findAll(Pageable pageable) {
        return courseRepository.findAll(pageable);
    }
}
