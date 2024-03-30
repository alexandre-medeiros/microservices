package com.himax.ead.course.domain.service;

import com.himax.ead.course.api.exceptionhandler.GetMessages;
import com.himax.ead.course.api.v1.model.UserDto;
import com.himax.ead.course.client.AuthUserClient;
import com.himax.ead.course.domain.enums.UserStatus;
import com.himax.ead.course.domain.exception.AlreadyExistsException;
import com.himax.ead.course.domain.exception.BusinessException;
import com.himax.ead.course.domain.model.Course;
import com.himax.ead.course.domain.model.CourseUser;
import com.himax.ead.course.domain.repository.CourseUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.UUID;

@AllArgsConstructor
@Service
public class CourseUserService {
    private CourseUserRepository repository;
    private AuthUserClient client;
    private CourseService service;

    @Transactional
    public CourseUser saveSubscriptionAndSendInfoToAuthUser(UUID courseId, UUID userId) {
        Course course = courseVerification(courseId);
        subscriptionVerification(courseId, userId);
        UserDto user = userVerification(userId);
        isUserValid(userId, user);
        CourseUser courseUser = save(course.convertToCourseUser(userId));
        client.postSubscriptionUserInCourse(courseId, userId);
        return courseUser;
    }

    private void isUserValid(UUID userId, UserDto user) {
        if(isNotActive(user)){
            throw new BusinessException(String.format("User with id %s is not ACTIVE ", userId));
        }
    }

    private boolean isNotActive(UserDto user) {
        return !user.getUserStatus().equals(UserStatus.ACTIVE);
    }

    private UserDto userVerification(UUID userId) {
        return client.findUserById(userId);
    }

    private void subscriptionVerification(UUID courseId, UUID userId) {
        if (subscriptionExists(courseId, userId)) {
            throw new AlreadyExistsException(GetMessages.getSubscriptionAlreadyExist(userId, courseId));
        }
    }

    private Course courseVerification(UUID courseId) {
        return service.findById(courseId);
    }

    private CourseUser save(CourseUser courseUser) {
        return repository.save(courseUser);
    }

    private boolean subscriptionExists(UUID courseId, UUID userId) {
        return repository.subscriptionExists(courseId, userId).isPresent();
    }
}
