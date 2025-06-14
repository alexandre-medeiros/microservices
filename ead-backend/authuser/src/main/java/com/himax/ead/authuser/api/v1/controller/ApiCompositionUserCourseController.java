package com.himax.ead.authuser.api.v1.controller;

import com.himax.ead.authuser.api.v1.model.course.CourseDto;
import com.himax.ead.authuser.client.CourseClient;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.UUID;

@Log4j2
@RestController
@AllArgsConstructor
@RequestMapping
@CrossOrigin(origins = "*", maxAge = 3600)
public class ApiCompositionUserCourseController {

    /*
        API Composition Pattern
        Implementation of queries that retrieve data from multiple Microservices,
        querying each of them through their respective APIs, combining these results.
        Pros:
            * Ideal for consultations in a Microservices Architecture
            * Simple way to consult and combine data from multiple services
            * Different ways of implement this Pattern

        Cons:
            * Reduced availability
            * Greater coupling between Microservices
            * Data consistency may be affected
     */
    private CourseClient client;

    @PreAuthorize("hasAnyRole('STUDENT')")
    @GetMapping("users/{userId}/courses")
    public Page<CourseDto> findAllCoursesByUser(
            @RequestHeader("Authorization") String token,
            @PathVariable UUID userId,
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return client.getAllCoursesByUser(userId, pageable, token);
    }
}
