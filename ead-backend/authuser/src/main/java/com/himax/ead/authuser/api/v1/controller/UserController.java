package com.himax.ead.authuser.api.v1.controller;

import com.himax.ead.authuser.api.v1.mapper.UserMapper;
import com.himax.ead.authuser.api.v1.model.auth.PasswordInputDto;
import com.himax.ead.authuser.api.v1.model.user.ImageInputDto;
import com.himax.ead.authuser.api.v1.model.user.UserInputDto;
import com.himax.ead.authuser.api.v1.model.user.UserOutputDto;
import com.himax.ead.authuser.domain.model.Users;
import com.himax.ead.authuser.domain.services.UserRegistryService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
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
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("users")
@CrossOrigin(origins = "*",maxAge = 3600)
public class UserController {

    private UserRegistryService service;
    private UserMapper mapper;

    @GetMapping
    public Page<UserOutputDto> findAll(@PageableDefault(page = 0, size = 10, sort = "username", direction = Sort.Direction.ASC)
            Pageable pageable){
        return new PageImpl<>(mapper.toListDto(service.findAll(pageable)));
    }

    @GetMapping("{id}")
    public UserOutputDto find(@PathVariable UUID id){
        return mapper.toDto(service.find(id));
    }

    @PutMapping("{id}")
    public UserOutputDto update(@PathVariable UUID id, @RequestBody @Valid UserInputDto dto){
        Users user = service.find(id);
        return mapper.toDto(service.update(mapper.updateUser(dto,user)));
    }

    @PutMapping("{id}/password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updatePassword(@PathVariable UUID id, @RequestBody @Valid PasswordInputDto dto){
        Users user = service.find(id);
        user.verifyCurrentAndNewPassword(dto.getOldPassword(), dto.getPassword());
        mapper.toDto(service.update(mapper.updatePassord(dto,user)));
    }

    @PutMapping("{id}/image")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateImage(@PathVariable UUID id, @RequestBody @Valid ImageInputDto dto){
        Users user = service.find(id);
        mapper.toDto(service.update(mapper.updateImage(dto,user)));
    }

    @DeleteMapping("id")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove (@PathVariable UUID id){
        service.remove(id);
    }
}
