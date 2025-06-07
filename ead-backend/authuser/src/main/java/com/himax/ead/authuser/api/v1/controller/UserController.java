package com.himax.ead.authuser.api.v1.controller;

import com.himax.ead.authuser.api.v1.mapper.UserMapper;
import com.himax.ead.authuser.api.v1.model.auth.PasswordInputDto;
import com.himax.ead.authuser.api.v1.model.user.ImageInputDto;
import com.himax.ead.authuser.api.v1.model.user.UserFilter;
import com.himax.ead.authuser.api.v1.model.user.UserInputDto;
import com.himax.ead.authuser.api.v1.model.user.UserOutputDto;
import com.himax.ead.authuser.core.config.security.AuthenticationCurrentUserService;
import com.himax.ead.authuser.core.config.security.UserDetailsImpl;
import com.himax.ead.authuser.domain.model.Users;
import com.himax.ead.authuser.domain.services.UserRegistryService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
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
@RequestMapping("users")
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserController {

    private UserRegistryService service;
    private AuthenticationCurrentUserService currentUserService;
    private UserMapper mapper;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public Page<UserOutputDto> findAll(
            @Valid UserFilter filter,
            @PageableDefault(page = 0, size = 10, sort = "username", direction = Sort.Direction.ASC) Pageable pageable) {
        log.debug("GET findAll users");
        Page<Users> all = service.findAllWithFilter(filter, pageable);
        List<UserOutputDto> userOutputDtos = mapper.toListDto(all);
        log.debug("GET findAll returned all users");
        log.info("All users returned successfully");
        return new PageImpl<>(userOutputDtos);
    }

    @PreAuthorize("hasAnyRole('STUDENT')")
    @GetMapping("{id}")
    public UserOutputDto find(@PathVariable UUID id) throws AccessDeniedException {
        log.debug("GET find user with id {}", id);
        UserDetailsImpl currentUser = currentUserService.getCurrentUser();

        if (currentUser.isNotTheSameUser(id)) {
            throw new AccessDeniedException("Forbidden");
        }

        Users user = service.find(id);
        log.debug("GET find returned user {}", user.toString());
        log.info("User with id {} returned successfully", id);
        return mapper.toDto(user);
    }

    @PutMapping("{id}")
    public UserOutputDto update(@PathVariable UUID id, @RequestBody @Valid UserInputDto dto) {
        Users user = service.find(id);
        Users mappedUser = mapper.updateUser(dto, user);
        log.debug("PUT update user {}", mappedUser.toString());
        Users updated = service.updateAndPublish(mappedUser);
        log.info("User updated successfully with id {}", user.getId());
        return mapper.toDto(updated);
    }

    @PutMapping("{id}/password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updatePassword(@PathVariable UUID id, @RequestBody @Valid PasswordInputDto dto) {
        log.debug("PUT update password to user with id {}", id);
        Users user = service.find(id);
        user.verifyCurrentAndNewPassword(dto.getOldPassword(), dto.getPassword());
        service.update(mapper.updatePassord(dto, user));
        log.debug("Password updated successfully to user with id {}", id);
        log.info("Password for user {} updated successfully", id);
    }

    @PutMapping("{id}/image")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateImage(@PathVariable UUID id, @RequestBody @Valid ImageInputDto dto) {
        log.debug("PUT update image to user with id {}", id);
        Users user = service.find(id);
        mapper.toDto(service.updateAndPublish(mapper.updateImage(dto, user)));
        log.debug("Image updated successfully to user with id {}", id);
        log.info("Image for user {} updated successfully", id);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable UUID id) {
        log.debug("DELETE remove user received with id {}", id);
        service.remove(id);
        log.debug("DELETE remove user with id {} deleted", id);
        log.info("User saved successfully with id {}", id);
    }
}
