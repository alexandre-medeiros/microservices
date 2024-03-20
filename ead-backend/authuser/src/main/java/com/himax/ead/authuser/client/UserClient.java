package com.himax.ead.authuser.client;

import com.himax.ead.authuser.api.v1.model.course.CourseDto;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import java.util.UUID;

@Log4j2
@AllArgsConstructor
@Component
public class UserClient {
    private RestTemplate restTemplate;
    private final String REQUEST_URI = "http://localhost:8082";

    public Page<CourseDto> getAllCourses(UUID userId, Pageable pageable){
//
//        String url = String.format("%s/courses?userId=%s", REQUEST_URI,userId);
//        List<CourseDto> searchResult = null;
//        ResponseEntity<ResponsePageDto<CourseDto>> result = null;
//
//        log.debug("Request URL: {} ", url);
//        log.info("Request URL: {} ", url);
//        try{
//            ParameterizedTypeReference<ResponsePageDto<CourseDto>> responseType = new ParameterizedTypeReference<ResponsePageDto<CourseDto>>() {};
//            result = restTemplate.exchange(url, HttpMethod.GET, null, responseType);
//            searchResult = result.getBody().getContent();
//            log.debug("Response Number of Elements: {} ", searchResult.size());
//        } catch (HttpStatusCodeException e){
//            log.error("Error request /courses {} ", e);
//        }
//        log.info("Ending request /courses userId {} ", userId);
//        return result.getBody();

        return null;
    }
}
