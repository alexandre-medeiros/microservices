package com.himax.ead.course.api.v1.controller;

import com.himax.ead.course.api.v1.mapper.course.CourseUserMapper;
import com.himax.ead.course.api.v1.model.CourseUserDto;
import com.himax.ead.course.api.v1.model.SubscriptionDto;
import com.himax.ead.course.api.v1.model.UserDto;
import com.himax.ead.course.domain.model.CourseUser;
import com.himax.ead.course.domain.service.CourseUserService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import java.util.UUID;

@Log4j2
@RestController
@AllArgsConstructor
@RequestMapping("courses")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ApiCompositionCourseUserController {

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
    private CourseUserService service;
    private CourseUserMapper mapper;

    @GetMapping("/{courseId}/users")
    public ResponseEntity<Object> findAllUsersByCourse(
            @PathVariable UUID courseId,
            @PageableDefault(page = 0, size = 10, sort = "username", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<UserDto> users = service.findAllUsers(courseId, pageable);

        if (!users.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(users);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping("/{courseId}/users/subscription")
    @ResponseStatus(HttpStatus.CREATED)
    public CourseUserDto saveSubscriptionUserInCourse(@PathVariable UUID courseId, @RequestBody SubscriptionDto dto) {
        CourseUser courseUser = service.saveSubscriptionAndSendInfoToAuthUser(courseId, dto.getUserId());
        return mapper.toDto(courseUser);
    }

    @DeleteMapping("/users/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUserInCourse(@PathVariable UUID userId) {
        service.deleteCourseUserByUserId(userId);
    }
}
