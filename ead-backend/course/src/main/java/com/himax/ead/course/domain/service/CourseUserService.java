package com.himax.ead.course.domain.service;

import com.himax.ead.course.domain.model.Course;
import com.himax.ead.course.domain.model.CourseUser;
import com.himax.ead.course.domain.repository.CourseUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.UUID;

@AllArgsConstructor
@Service
public class CourseUserService {
    CourseUserRepository courseUserRepository;
    //AuthUserClient authUserClient;

    public boolean existsByCourseAndUserId(Course courseModel, UUID userId) {
        return courseUserRepository.existsByCourseAndUserId(courseModel, userId);
    }

    public CourseUser save(CourseUser courseUserModel) {
        return courseUserRepository.save(courseUserModel);
    }

//    @Transactional
//    public CourseUser saveAndSendSubscriptionUserInCourse(CourseUser courseUserModel) {
//        courseUserModel = courseUserRepository.save(courseUserModel);
//        authUserClient.postSubscriptionUserInCourse(courseUserModel.getCourse().getId(), courseUserModel.getUserId());
//        return courseUserModel;
//    }
}
