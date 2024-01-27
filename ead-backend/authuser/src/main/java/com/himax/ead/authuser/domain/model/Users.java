package com.himax.ead.authuser.domain.model;

import com.himax.ead.authuser.domain.enums.UserStatus;
import com.himax.ead.authuser.domain.enums.UserType;
import com.himax.ead.authuser.domain.exception.BusinessException;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.UUID;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "users")
public class Users implements Serializable{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
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


    public void verifyCurrentAndNewPassword(String entered, String newPassword){
        passwordEnteredIsTheSameAsRegistered(entered);
        newPasswordIsEqualsAsRegistered(newPassword);
    }

    private void passwordEnteredIsTheSameAsRegistered(String entered){
        if(!this.password.equals(entered)){
            throw new BusinessException("Current password entered is different of the registered");
        }
    }

    private void newPasswordIsEqualsAsRegistered(String newPassword){
        if(this.password.equals(newPassword)){
            throw new BusinessException("New password entered is equals as registered");
        }
    }
}
