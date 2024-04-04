package com.himax.ead.authuser.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import java.util.UUID;

@Service
public class UrlHelper {

    @Value("${ead.api.url.course}")
    private String COURSE_URI;

    public String getDeleteUserInCourseUrl(UUID userId) {
        return COURSE_URI + "/courses/users/" + userId;
    }

    public String getFindAllCoursesUrl(UUID userId, Pageable pageable) {
        String url = COURSE_URI + "/courses";
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(url)
                .queryParam("userId", userId)
                .queryParam("sort", pageable.getSort().toString().replace(": ", ","))
                .queryParam("page", pageable.getPageNumber())
                .queryParam("size", pageable.getPageSize());

        return uriBuilder.toUriString();
    }
}
