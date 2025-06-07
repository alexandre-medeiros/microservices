package com.easydiner.domain.model;

import com.himax.ead.authuser.api.v1.model.user.UserEventDto;
import com.himax.ead.authuser.core.config.security.UserDetailsImpl;
import com.himax.ead.authuser.domain.enums.UserStatus;
import com.himax.ead.authuser.domain.enums.UserType;
import com.himax.ead.authuser.domain.exception.BusinessException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@Log4j2
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "users")
public class Users implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(nullable = false, unique = true, length = 50)
    private String username;
    @Column(nullable = false, unique = true, length = 50)
    private String email;
    @Column(nullable = false, length = 255)
    private String password;
    @Column(nullable = false, length = 150)
    private String fullName;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserType userType;
    @Column(length = 20)
    private String phoneNumber;
    @Column(length = 20)
    private String cpf;
    @Column
    private String imageUrl;
    @CreationTimestamp
    @Column(nullable = false)
    private OffsetDateTime creationDate;
    @UpdateTimestamp
    @Column(nullable = false)
    private OffsetDateTime lastUpdateDate;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "users_id"),
            inverseJoinColumns = @JoinColumn(name = "roles_id"))
    private Set<Roles> roles;

    public void addRole(Roles role) {
        if (roles == null) {
            roles = new HashSet<>();
        }
        roles.add(role);
    }

    public UserEventDto toDto() {
        UserEventDto dto = new UserEventDto();
        BeanUtils.copyProperties(this, dto);
        dto.setUserStatus(this.getUserStatus().toString());
        dto.setUserType(this.getUserType().toString());
        return dto;
    }

    public UserDetailsImpl toUserDetails() {
        return UserDetailsImpl.build(this);
    }

    public void verifyCurrentAndNewPassword(String entered, String newPassword) {
        validPasswordAsRegistered(entered);
        newPasswordIsEqualsAsRegistered(newPassword);
    }

    public void validPasswordAsRegistered(String entered) {
        if (!this.password.equals(entered)) {
            log.warn("Current password entered to user id {} is different of the registered", this.id);
            throw new BusinessException("Current password entered is different of the registered");
        }
    }

    private void newPasswordIsEqualsAsRegistered(String newPassword) {
        if (this.password.equals(newPassword)) {
            log.warn("New password entered to user id {} is equals as registered", this.id);
            throw new BusinessException("New password entered is equals as registered");
        }
    }
}
