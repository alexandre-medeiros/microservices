package com.himax.ead.authuser.domain.services;

import com.himax.ead.authuser.api.exceptionhandler.GetMessages;
import com.himax.ead.authuser.domain.exception.AlreadyExistsException;
import com.himax.ead.authuser.domain.model.UserCourse;
import com.himax.ead.authuser.domain.model.Users;
import com.himax.ead.authuser.domain.repository.UserCourseRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

@AllArgsConstructor
@Service
public class UserCourseService {
    private UserCourseRepository userCourseRepository;
    private UserRegistryService userService;

    public boolean existsByUserAndCourseId(Users userModel, UUID courseId) {
        return userCourseRepository.existsByUserAndCourseId(userModel, courseId);
    }
    public boolean subscriptionExists(UUID userId, UUID courseId) {
        return userCourseRepository.subscriptionExists(userId, courseId.toString()).isPresent();
    }

    @Transactional
    public UserCourse save(UUID userId, UUID courseId) {
        Users user = userVerification(userId);
        subscriptionVerification(userId, courseId);

        UserCourse userCourse = new UserCourse(null, courseId.toString(), user);
        return userCourseRepository.save(userCourse);
    }

    private void subscriptionVerification(UUID userId, UUID courseId) {
        if(subscriptionExists(userId, courseId)){
            throw new AlreadyExistsException(GetMessages.getSubscriptionAlreadyExist(userId, courseId));
        }
    }

    private Users userVerification(UUID userId) {
        return userService.find(userId);
    }
}
