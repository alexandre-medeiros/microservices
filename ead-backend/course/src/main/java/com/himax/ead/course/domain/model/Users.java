package com.himax.ead.course.domain.model;

import com.himax.ead.course.core.config.security.UserDetailsImpl;
import com.himax.ead.course.domain.enums.UserStatus;
import com.himax.ead.course.domain.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.io.Serial;
import java.io.Serializable;
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

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private UUID id;
    @Column(nullable = false, unique = true, length = 50)
    private String email;
    @Column(nullable = false, length = 150)
    private String fullName;
    @Column(nullable = false)
    private String userStatus;
    @Column(nullable = false)
    private String userType;
    @Column(length = 20)
    private String cpf;
    @Column
    private String imageUrl;

    @ManyToMany(mappedBy = "users", fetch = FetchType.LAZY)
    private Set<Course> courses;

    public boolean isInstructor() {
        return this.getUserType().equals(UserType.INSTRUCTOR.toString());
    }

    public boolean isAdministrator() {
        return this.getUserType().equals(UserType.ADMIN.toString());
    }

    public boolean isActive() {
        return this.getUserStatus().equals(UserStatus.ACTIVE.toString());
    }

    public boolean isNotTheSame(UserDetailsImpl authUser) {
        return this.getId().equals(authUser.getId());
    }
}
