package com.himax.ead.course.domain.service;

import com.himax.ead.course.api.v1.model.CourseFilter;
import com.himax.ead.course.domain.enums.CourseLevel;
import com.himax.ead.course.domain.enums.CourseStatus;
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

    public Page<Course> findAllWithFilter(CourseFilter filter, UUID userId, Pageable pageable) {
        CourseLevel courseLevel = filter.getCourseLevel();
        CourseStatus courseStatus = filter.getCourseStatus();
        String name = filter.getName();
        return courseRepository.findAllWithFilter(courseLevel, courseStatus, name, userId, pageable);
    }
}
