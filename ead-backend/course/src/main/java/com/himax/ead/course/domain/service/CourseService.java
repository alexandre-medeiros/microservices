package com.himax.ead.course.domain.service;

import com.himax.ead.course.api.exceptionhandler.GetMessages;
import com.himax.ead.course.api.v1.model.CourseFilter;
import com.himax.ead.course.client.AuthUserClient;
import com.himax.ead.course.domain.enums.CourseLevel;
import com.himax.ead.course.domain.enums.CourseStatus;
import com.himax.ead.course.domain.exception.EntityNotFoundException;
import com.himax.ead.course.domain.model.Course;
import com.himax.ead.course.domain.repository.CourseRepository;
import com.himax.ead.course.domain.repository.CourseUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.UUID;

@AllArgsConstructor
@Service
public class CourseService {

    private CourseUserRepository courseUserRepository;
    private CourseRepository repository;
    private AuthUserClient client;

    @Transactional
    public void delete(Course course) {
        repository.delete(course);
        if (existsCourseUser(course)) {
            client.deleteCourseInAuthUser(course.getId());
        }
    }

    @Transactional
    public Course save(Course course) {
        return repository.save(course);
    }

    public Course findById(UUID courseId) {
        return repository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException(GetMessages.getCourseNotExist(courseId)));
    }

    public Page<Course> findAllWithFilter(CourseFilter filter, UUID userId, Pageable pageable) {
        CourseLevel courseLevel = filter.getCourseLevel();
        CourseStatus courseStatus = filter.getCourseStatus();
        String name = filter.getName();
        return repository.findAllWithFilter(courseLevel, courseStatus, name, userId, pageable);
    }

    private boolean existsCourseUser(Course course) {
        return courseUserRepository
                .findAllCourseUserIntoCourse(course.getId())
                .isEmpty();
    }
}
