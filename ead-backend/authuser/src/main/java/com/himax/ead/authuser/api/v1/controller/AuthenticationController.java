package com.himax.ead.authuser.api.v1.controller;

import com.himax.ead.authuser.api.v1.mapper.UserMapper;
import com.himax.ead.authuser.api.v1.model.auth.RegistrationInputDto;
import com.himax.ead.authuser.api.v1.model.security.LoginDto;
import com.himax.ead.authuser.api.v1.model.user.UserOutputDto;
import com.himax.ead.authuser.domain.enums.RoleType;
import com.himax.ead.authuser.domain.enums.UserStatus;
import com.himax.ead.authuser.domain.enums.UserType;
import com.himax.ead.authuser.domain.model.Roles;
import com.himax.ead.authuser.domain.model.Users;
import com.himax.ead.authuser.domain.services.RolesService;
import com.himax.ead.authuser.domain.services.UserRegistryService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;

@Log4j2
@AllArgsConstructor
@RestController
@RequestMapping("auth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthenticationController {

    private UserRegistryService service;
    private RolesService rolesService;
    private UserMapper mapper;
    private PasswordEncoder passwordEncoder;

    @PostMapping("signup")
    @ResponseStatus(HttpStatus.CREATED)
    public UserOutputDto register(@RequestBody @Valid RegistrationInputDto dto) {
        log.debug("POST register new user received {}", dto.toString());
        Users user = mapper.toDomain(dto);
        Roles role = rolesService.findByRoleName(RoleType.ROLE_STUDENT);
        user.setUserStatus(UserStatus.ACTIVE);
        user.setUserType(UserType.STUDENT);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.addRole(role);
        user = service.saveAndPublish(user);
        log.debug("POST register user saved {}", user.toString());
        log.info("User saved successfully with id {}", user.getId());
        return mapper.toDto(user);
    }

    @PostMapping("login")
    public UserOutputDto login(@RequestBody LoginDto login) {
        Users user = service.findbyUserName(login.getUsername());
        return mapper.toDto(user);
    }
}
