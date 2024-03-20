package com.himax.ead.authuser.domain.services;

import com.himax.ead.authuser.domain.model.UserCourse;
import com.himax.ead.authuser.domain.model.Users;
import com.himax.ead.authuser.domain.repository.UserCourseRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.UUID;

@AllArgsConstructor
@Service
public class UserCourseService {
    private UserCourseRepository userCourseRepository;

    public boolean existsByUserAndCourseId(Users userModel, UUID courseId) {
        return userCourseRepository.existsByUserAndCourseId(userModel, courseId);
    }

    public UserCourse save(UserCourse userCourseModel) {
        return userCourseRepository.save(userCourseModel);
    }
}
