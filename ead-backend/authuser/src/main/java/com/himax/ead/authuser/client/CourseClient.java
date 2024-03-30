package com.himax.ead.authuser.client;

import com.himax.ead.authuser.api.v1.model.course.CourseDto;
import com.himax.ead.authuser.api.v1.model.page.ResponsePageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import java.util.List;
import java.util.UUID;

@Log4j2
@RequiredArgsConstructor
@Component
public class CourseClient {
    @Value("${ead.api.url.authuser}")
    private String REQUEST_URI;
    private final RestTemplate restTemplate;

    public Page<CourseDto> getAllCourses(UUID userId, Pageable pageable){
        String url = String.format("%s/courses", REQUEST_URI);
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(url)
                .queryParam("userId",userId)
                .queryParam("sort", pageable.getSort().toString().replace(": ", ","))
                .queryParam("page", pageable.getPageNumber())
                .queryParam("size", pageable.getPageSize());
        List<CourseDto> searchResult = null;
        ResponseEntity<ResponsePageDto<CourseDto>> result = null;

        log.debug("Request URL: {} ", url);
        log.info("Request URL: {} ", url);
        try{
            ParameterizedTypeReference<ResponsePageDto<CourseDto>> responseType = new ParameterizedTypeReference<ResponsePageDto<CourseDto>>() {};
            result = restTemplate.exchange(uriBuilder.toUriString(), HttpMethod.GET, null, responseType);
            searchResult = result.getBody().getContent();
            log.debug("Response Number of Elements: {} ", searchResult.size());
        } catch (HttpStatusCodeException e){
            log.error("Error request /courses {} ", e);
        }
        log.info("Ending request /courses userId {} ", userId);
        return result.getBody();
    }
}