package com.himax.ead.authuser.domain.repository;

import com.himax.ead.authuser.domain.model.UserCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
public interface UserCourseRepository extends JpaRepository<UserCourse, UUID> {

    void deleteByCourseId(String courseId);

    @Query(value = "select * from users_courses " +
            "where course_id = :courseId " +
            "  and user_id = :userId", nativeQuery = true)
    Optional<UserCourse> subscriptionExists(UUID userId, String courseId);


    @Query(value = "select * from users_courses " +
            "where user_id = :userId", nativeQuery = true)
    List<UserCourse> subscriptionExistsByUser(UUID userId);
}