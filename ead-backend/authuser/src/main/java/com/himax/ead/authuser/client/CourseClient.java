package com.himax.ead.authuser.client;

import com.himax.ead.authuser.api.v1.model.course.CourseDto;
import com.himax.ead.authuser.api.v1.model.page.ResponsePageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import java.util.List;
import java.util.UUID;

@Log4j2
@RequiredArgsConstructor
@Component
public class CourseClient {

    private final RestTemplate restTemplate;
    private final UrlHelper helper;

    public Page<CourseDto> getAllCourses(UUID userId, Pageable pageable) {
        String url = helper.getFindAllCoursesUrl(userId, pageable);
        List<CourseDto> searchResult = null;
        ResponseEntity<ResponsePageDto<CourseDto>> result = null;

        log.debug("Request URL: {} ", url);
        log.info("Request URL: {} ", url);

        try {
            ParameterizedTypeReference<ResponsePageDto<CourseDto>> responseType = new ParameterizedTypeReference<ResponsePageDto<CourseDto>>() {
            };
            result = restTemplate.exchange(url, HttpMethod.GET, null, responseType);
            searchResult = result.getBody().getContent();
            log.debug("Response Number of Elements: {} ", searchResult.size());
        } catch (HttpStatusCodeException e) {
            log.error("Error request /courses {} ", e);
        }
        log.info("Ending request /courses userId {} ", userId);
        return result.getBody();
    }

    public void deleteUserInCourse(UUID id) {
        String url = helper.getDeleteUserInCourseUrl(id);
        log.debug(url);
        log.info(url);
        try {
            restTemplate.delete(url);
        } catch (HttpStatusCodeException e) {
            if (!e.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
                throw e;
            }
        }

        log.info("Ending delete user subscription with user id {} in COURSE", id);
    }
}
