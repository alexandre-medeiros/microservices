package com.himax.ead.course.api.exceptionhandler;

import com.himax.ead.course.domain.model.Users;
import java.util.UUID;
public class GetMessages {

    private static final String COURSE_NOT_EXIST = "Course with id %s does not exist";
    private static final String MODULE_NOT_EXIST = "Module with id %s does not exist";
    private static final String LESSON_NOT_EXIST = "Lesson with id %s does not exist";
    private static final String SUBSCRIPTION_ALREADY_EXIST = "Subscription with user id %s and course id %s already exists";

    public static String getCourseNotExist(UUID id) {
        return String.format(COURSE_NOT_EXIST, id);
    }

    public static String getLessonNotExist(UUID id) {
        return String.format(LESSON_NOT_EXIST, id);
    }

    public static String getModuleNotExist(UUID id) {
        return String.format(MODULE_NOT_EXIST, id);
    }

    public static String getSubscriptionAlreadyExist(UUID userId, UUID courseId) {
        return String.format(SUBSCRIPTION_ALREADY_EXIST, userId, courseId);
    }

    public static String getUserInvalidToCreateCourse(Users user) {
        if (!user.isActive()) {
            return String.format("User with id %s is NOT ACTIVE", user.getId());
        }

        if (!user.isInstructor() && !user.isAdministrator()) {
            return String.format(String.format("User with id %s is not INSTRUCTOR OR ADMINISTRATOR", user.getId()));
        }

        return null;
    }
}
