package com.himax.ead.course.client;

import com.himax.ead.course.api.v1.model.CourseUserDto;
import com.himax.ead.course.api.v1.model.ResponsePageDto;
import com.himax.ead.course.api.v1.model.UserDto;
import com.himax.ead.course.domain.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import java.util.List;
import java.util.UUID;

@Log4j2
@RequiredArgsConstructor
@Service
public class AuthUserClient {

    public static final String REQUEST_URL = "Request URL: {} ";

    private final RestTemplate restTemplate;
    private final UrlHelper urlHelper;

    public Page<UserDto> findAllUsers(UUID courseId, Pageable pageable) {
        String url = urlHelper.getFindAllUsersUrl(courseId, pageable);
        List<UserDto> searchResult = null;
        ResponseEntity<ResponsePageDto<UserDto>> result = null;

        log.debug(REQUEST_URL, url);
        log.info(REQUEST_URL, url);
        ParameterizedTypeReference<ResponsePageDto<UserDto>> responseType = new ParameterizedTypeReference<>() {
        };

        try {
            result = restTemplate.exchange(url, HttpMethod.GET, null, responseType);
            searchResult = result.getBody().getContent();
            log.debug("Response Number of Elements: {} ", searchResult.size());
        } catch (HttpStatusCodeException e) {
            log.error("Error request /users {}", e);
        }

        log.info("Ending request /users courseId {} ", courseId);
        return result.getBody();
    }

    public UserDto findUserById(UUID id) {
        String url = urlHelper.getFindUserByIdUrl(id);
        ResponseEntity<UserDto> user = null;

        log.debug(REQUEST_URL, url);
        log.info(REQUEST_URL, url);

        try {
            user = restTemplate.exchange(url, HttpMethod.GET, null, UserDto.class);
            log.debug("Response User: {} ", user);
        } catch (HttpStatusCodeException e) {
            log.error("Error request /users {} ", e);

            if (e.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
                throw new EntityNotFoundException(String.format("User with id %s do not exist", id));
            } else {
                throw e;
            }
        }

        log.info("Ending request /users id {} ", id);
        return user.getBody();
    }

    public void postSubscriptionUserInCourse(UUID courseId, UUID userId) {
        String url = urlHelper.getPostSubscriptionUrl(userId);
        CourseUserDto courseUserDto = new CourseUserDto(courseId, userId, null);

        log.debug(REQUEST_URL, url);
        log.info(REQUEST_URL, url);

        restTemplate.postForObject(url, courseUserDto, String.class);
        log.info("Ending post info subscription with course id {} and user id {} ", courseId, userId);
    }

    public void deleteCourseInAuthUser(UUID id) {
        String url = urlHelper.getDeleteCourseInAuthUserUrl(id);

        log.debug(REQUEST_URL, url);
        log.info(REQUEST_URL, url);

        restTemplate.delete(url);
        log.info("Ending delete userCourse subscription with course id {}", id);
    }
}