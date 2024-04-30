package com.himax.ead.course.domain.service;

import com.himax.ead.course.api.exceptionhandler.GetMessages;
import com.himax.ead.course.api.v1.model.NotificationCommandDto;
import com.himax.ead.course.api.v1.publishers.NotificationCommandPublisher;
import com.himax.ead.course.domain.exception.BusinessException;
import com.himax.ead.course.domain.model.Course;
import com.himax.ead.course.domain.model.Users;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

@Service
@Log4j2
@RequiredArgsConstructor
public class CourseUserService {

    private final CourseService courseService;
    private final UserService userService;
    private final NotificationCommandPublisher notificationCommandPublisher;

    @Transactional
    public Course saveSubscriptionUserInCourse(UUID courseId, UUID userId) {
        Course course = courseService.findById(courseId);
        Users user = userService.findById(userId);
        boolean subscriptionNotExists = course.getUsers().stream()
                .noneMatch(u -> u.equals(user));

        if (subscriptionNotExists) {
            course.addUser(user);
            notifyUser(course, user);
            return course;
        } else {
            throw new BusinessException(GetMessages.getSubscriptionAlreadyExist(userId, courseId));
        }
    }

    private void notifyUser(Course course, Users user) {
        try {
            NotificationCommandDto dto = new NotificationCommandDto();
            dto.setUserId(user.getId());
            dto.setTitle("Seja bem-vindo(a) ao Curso " + course.getName());
            dto.setMessage(user.getFullName() + " a sua inscrição foi realizada com sucesso !!!");
            notificationCommandPublisher.publishNotificationCommand(dto);
        } catch (Exception e) {
            log.warn("error sending notification");
        }
    }
}
