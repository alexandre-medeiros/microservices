package com.himax.ead.authuser.domain.repository;

import com.himax.ead.authuser.domain.model.UserCourse;
import com.himax.ead.authuser.domain.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.Optional;
import java.util.UUID;
public interface UserCourseRepository extends JpaRepository<UserCourse, UUID> {
    boolean existsByUserAndCourseId(Users user, UUID courseId);

    @Query(value="select * from users_courses " +
            "where course_id = :courseId " +
            "  and user_id = :userId", nativeQuery = true)
    Optional<UserCourse> subscriptionExists(UUID userId, String courseId);
}