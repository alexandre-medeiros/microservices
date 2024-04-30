package com.himax.ead.authuser.domain.services;

import com.himax.ead.authuser.domain.enums.RoleType;
import com.himax.ead.authuser.domain.exception.EntityNotFoundException;
import com.himax.ead.authuser.domain.model.Roles;
import com.himax.ead.authuser.domain.repository.RoleRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@AllArgsConstructor
@Service
public class RolesService {

    private RoleRepository repository;

    public Roles findByRoleName(RoleType roleName) {
        return repository.findByRoleName(roleName)
                .orElseThrow(() ->
                        new EntityNotFoundException(String.format("Role with name %s do not exist", roleName)));
    }
}
