package com.himax.ead.course.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import java.util.UUID;

@Service
public class UrlHelper {

    @Value("${ead.api.url.authuser}")
    private String AUTH_USER_URI;

    public String getFindUserByIdUrl(UUID id) {
        return AUTH_USER_URI + "/users/" + id;
    }

    public String getFindAllUsersUrl(UUID courseId, Pageable pageable) {
        String url = AUTH_USER_URI + "/users";
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(url)
                .queryParam("courseId", courseId)
                .queryParam("sort", pageable.getSort().toString().replace(": ", ","))
                .queryParam("page", pageable.getPageNumber())
                .queryParam("size", pageable.getPageSize());

        return uriBuilder.toUriString();
    }

    public String getPostSubscriptionUrl(UUID userId) {
        return AUTH_USER_URI + "/users/" + userId + "/courses/subscription";
    }

    public String getDeleteSubscriptionUrl(UUID userId) {
        return AUTH_USER_URI + "/users/" + userId + "/courses/subscription";
    }
}
