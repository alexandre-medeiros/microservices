package com.himax.ead.course.domain.service;

import com.himax.ead.course.api.exceptionhandler.GetMessages;
import com.himax.ead.course.domain.exception.BusinessException;
import com.himax.ead.course.domain.model.Course;
import com.himax.ead.course.domain.model.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CourseUser {

    private final CourseService courseService;
    private final UserService userService;

    @Transactional
    public Course saveSubscriptionUserInCourse(UUID courseId, UUID userId) {
        Course course = courseService.findById(courseId);
        Users user = userService.findById(userId);

        boolean subscriptionNoExists = course.getUsers().stream()
                .noneMatch(u -> u.equals(user));

        if (subscriptionNoExists) {
            course.addUser(user);
            return course;
        } else {
            throw new BusinessException(GetMessages.getSubscriptionAlreadyExist(userId, courseId));
        }
    }
}
