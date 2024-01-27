package com.himax.ead.authuser.api.v1.controller;

import com.himax.ead.authuser.api.v1.mapper.UserMapper;
import com.himax.ead.authuser.api.v1.model.auth.RegistrationInputDto;
import com.himax.ead.authuser.api.v1.model.user.UserOutputDto;
import com.himax.ead.authuser.domain.services.UserRegistryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping("auth")
@CrossOrigin(origins = "*",maxAge = 3600)
public class AuthenticationController {

    private UserRegistryService service;
    private UserMapper mapper;

    @PostMapping("signup")
    @ResponseStatus(HttpStatus.CREATED)
    public UserOutputDto register(@RequestBody @Valid RegistrationInputDto dto){
        return mapper.toDto(service.create(mapper.toDomain(dto)));
    }
}
