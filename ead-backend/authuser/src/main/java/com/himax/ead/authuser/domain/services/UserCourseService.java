package com.himax.ead.authuser.domain.services;

import com.himax.ead.authuser.api.exceptionhandler.GetMessages;
import com.himax.ead.authuser.domain.exception.AlreadyExistsException;
import com.himax.ead.authuser.domain.model.UserCourse;
import com.himax.ead.authuser.domain.model.Users;
import com.himax.ead.authuser.domain.repository.UserCourseRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

@Service
public class UserCourseService {

    private final UserCourseRepository userCourseRepository;
    private final UserRegistryService userService;

    public UserCourseService(UserCourseRepository userCourseRepository, @Lazy UserRegistryService userService) {
        this.userCourseRepository = userCourseRepository;
        this.userService = userService;
    }

    public boolean subscriptionExists(UUID userId, UUID courseId) {
        return userCourseRepository.subscriptionExists(userId, courseId.toString()).isPresent();
    }

    public boolean subscriptionExistsByUser(UUID userId) {
        return !userCourseRepository.subscriptionExistsByUser(userId).isEmpty();
    }

    @Transactional
    public UserCourse save(UUID userId, UUID courseId) {
        Users user = userVerification(userId);
        subscriptionVerification(userId, courseId);

        UserCourse userCourse = new UserCourse(null, courseId.toString(), user);
        return userCourseRepository.save(userCourse);
    }

    @Transactional
    public void deleteUserCourse(String courseId) {
        userCourseRepository.deleteByCourseId(courseId);
    }

    private void subscriptionVerification(UUID userId, UUID courseId) {
        if (subscriptionExists(userId, courseId)) {
            throw new AlreadyExistsException(GetMessages.getSubscriptionAlreadyExist(userId, courseId));
        }
    }

    private Users userVerification(UUID userId) {
        return userService.find(userId);
    }
}
