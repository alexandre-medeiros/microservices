package com.himax.ead.course.domain.service;

import com.himax.ead.course.api.exceptionhandler.GetMessages;
import com.himax.ead.course.api.v1.model.CourseFilter;
import com.himax.ead.course.core.config.security.AuthenticationCurrentUserService;
import com.himax.ead.course.core.config.security.UserDetailsImpl;
import com.himax.ead.course.domain.enums.CourseLevel;
import com.himax.ead.course.domain.enums.CourseStatus;
import com.himax.ead.course.domain.exception.BusinessException;
import com.himax.ead.course.domain.exception.EntityNotFoundException;
import com.himax.ead.course.domain.model.Course;
import com.himax.ead.course.domain.model.Users;
import com.himax.ead.course.domain.repository.CourseRepository;
import com.himax.ead.course.domain.repository.ModuleRepository;
import com.himax.ead.course.domain.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

@AllArgsConstructor
@Service
public class CourseService {

    private final ModuleRepository moduleRepository;
    private final UserRepository userRepository;
    private CourseRepository repository;
    private UserService userService;
    private LessonService lessonService;
    private ModuleService moduleService;
    private AuthenticationCurrentUserService authService;

    @Transactional
    public Course save(Course course) {
        Users user = userService.findById(course.getUserInstructor());
        UserDetailsImpl authUser = authService.getCurrentUser();

        if (user.isNotTheSame(authUser)) {
            throw new AccessDeniedException("Forbidden");
        }

        if (isUserNotValidToCreateCourse(user)) {
            throw new BusinessException(GetMessages.getUserInvalidToCreateCourse(user));
        }

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

    @Transactional
    public void delete(Course course) {
        lessonService.deleteLessonByCourseId(course.getId());
        moduleService.deleteModulesByCourse(course.getId());
        userService.deleteUsersByCourseId(course.getId());
        repository.delete(course);
    }

    private boolean isUserNotValidToCreateCourse(Users user) {
        return !(user.isActive() &&
                (user.isInstructor() || user.isAdministrator()));
    }

}
