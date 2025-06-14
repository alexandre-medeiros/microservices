package com.himax.ead.authuser.api.v1.controller;

import com.himax.ead.authuser.api.v1.mapper.UserMapper;
import com.himax.ead.authuser.api.v1.model.course.InstructorDto;
import com.himax.ead.authuser.api.v1.model.user.UserOutputDto;
import com.himax.ead.authuser.domain.services.UserRegistryService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;

@Log4j2
@RestController
@AllArgsConstructor
@RequestMapping("instructor")
@CrossOrigin(origins = "*", maxAge = 3600)
public class InstructorController {

    private UserRegistryService service;
    private UserMapper mapper;

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("subscription")
    public UserOutputDto subscription(@RequestBody @Valid InstructorDto dto) {
        return mapper.toDto(service.saveInstructor(dto.getUserId()));
    }
}
