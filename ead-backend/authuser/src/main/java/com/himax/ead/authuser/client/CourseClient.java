package com.himax.ead.authuser.client;

import com.himax.ead.authuser.api.v1.model.course.CourseDto;
import com.himax.ead.authuser.api.v1.model.page.ResponsePageDto;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import java.util.UUID;

@Log4j2
@RequiredArgsConstructor
@Component
public class CourseClient {

    private final RestTemplate restTemplate;
    private final UrlHelper helper;

    /*
        Retry is a possibility but the purpose of Circuit Breaker is avoid to bomb the end point with requests
        @Retry(name = "retryInstance", fallbackMethod = "retryFallback")
     */
    @Retry(name = "retryInstance")
    @CircuitBreaker(name = "circuitBreakerInstance")
    public Page<CourseDto> getAllCoursesByUser(UUID userId, Pageable pageable, String token) {
        String url = helper.getFindAllCoursesByUserUrl(userId, pageable);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        HttpEntity<String> requestEntity = new HttpEntity<>("parameters", headers);

        log.debug("Request URL: {} ", url);
        log.info("Request URL: {} ", url);

        ParameterizedTypeReference<ResponsePageDto<CourseDto>> responseType = new ParameterizedTypeReference<>() {
        };
        ResponseEntity<ResponsePageDto<CourseDto>> result = restTemplate.exchange(url, HttpMethod.GET, requestEntity, responseType);
        Page<CourseDto> searchResult = result.getBody();
        log.debug("Response Number of Elements: {} ", searchResult.getContent().size());
        log.info("Ending request /courses userId {} ", userId);
        return searchResult;
    }
    /*
        //CallBack method executed when all retries fail
        public Page<CourseDto> retryFallback(UUID userId, Pageable pageable, Throwable t) {
            log.error("Inside retry fallback, cause {}", t.toString());
            return new PageImpl<>(new ArrayList<>());
        }
     */
}
