package com.himax.ead.course.api;

import java.util.UUID;
public class GetMessages {
    private static final String COURSE_NOT_EXIST = "Course with id %s does not exist";
    private static final String MODULE_NOT_EXIST = "Module with id %s does not exist";
    private static final String LESSON_NOT_EXIST = "Lesson with id %s does not exist";

    public static String getCourseNotExist(UUID id) {
        return String.format(COURSE_NOT_EXIST, id);
    }

    public static String getLessonNotExist(UUID id) {
        return String.format(LESSON_NOT_EXIST, id);
    }

    public static String getModuleNotExist(UUID id) {
        return String.format(MODULE_NOT_EXIST, id);
    }
}
