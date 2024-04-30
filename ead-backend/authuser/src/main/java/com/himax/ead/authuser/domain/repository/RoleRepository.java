package com.himax.ead.authuser.domain.repository;

import com.himax.ead.authuser.domain.enums.RoleType;
import com.himax.ead.authuser.domain.model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface RoleRepository extends JpaRepository<Roles, UUID> {

    Optional<Roles> findByRoleName(RoleType roleName);
}
