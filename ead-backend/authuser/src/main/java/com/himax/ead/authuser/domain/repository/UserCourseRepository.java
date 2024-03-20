package com.himax.ead.authuser.domain.repository;

import com.himax.ead.authuser.domain.model.UserCourse;
import com.himax.ead.authuser.domain.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;
public interface UserCourseRepository extends JpaRepository<UserCourse, UUID> {
    boolean existsByUserAndCourseId(Users user, UUID courseId);
}