package com.himax.ead.authuser.api.exceptionhandler;

import java.util.UUID;
public class GetMessages {
    private static final String SUBSCRIPTION_ALREADY_EXIST = "Subscription with user id %s and course id %s already exists";

    public static String getSubscriptionAlreadyExist(UUID userId, UUID courseId) {
        return String.format(SUBSCRIPTION_ALREADY_EXIST, userId, courseId);
    }
}
