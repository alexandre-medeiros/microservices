package com.himax.ead.course.api.v1.controller;

import com.himax.ead.course.api.v1.mapper.course.CourseMapper;
import com.himax.ead.course.api.v1.mapper.user.UserMapper;
import com.himax.ead.course.api.v1.model.CourseUserDto;
import com.himax.ead.course.api.v1.model.SubscriptionDto;
import com.himax.ead.course.api.v1.model.UserDto;
import com.himax.ead.course.api.v1.model.UserFilter;
import com.himax.ead.course.domain.model.Course;
import com.himax.ead.course.domain.model.Users;
import com.himax.ead.course.domain.service.CourseUserService;
import com.himax.ead.course.domain.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@Log4j2
@RestController
@AllArgsConstructor
@RequestMapping("courses")
@CrossOrigin(origins = "*", maxAge = 3600)
public class CourseUserController {

    private UserService service;
    private CourseUserService courseUserService;
    private UserMapper userMapper;
    private CourseMapper courseMapper;

    @GetMapping("/{courseId}/users")
    public Page<UserDto> findAllUsersByCourse(@Valid UserFilter filter, @PathVariable UUID courseId,
                                              @PageableDefault(page = 0, size = 10, sort = "fullName", direction = Sort.Direction.ASC) Pageable pageable) {
        List<Users> users = service.findAllWithFilter(filter, courseId, pageable);
        List<UserDto> usersDto = userMapper.toListDto(users);
        return new PageImpl<>(usersDto, pageable, usersDto.size());
    }

    @PostMapping("/{courseId}/users/subscription")
    @ResponseStatus(HttpStatus.CREATED)
    public CourseUserDto saveSubscriptionUserInCourse(@PathVariable UUID courseId, @RequestBody SubscriptionDto dto) {
        Course course = courseUserService.saveSubscriptionUserInCourse(courseId, dto.getUserId());
        return courseMapper.toCourseUserDto(course);
    }
}
