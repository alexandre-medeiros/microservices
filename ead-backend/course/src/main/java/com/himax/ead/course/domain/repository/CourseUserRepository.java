package com.himax.ead.course.domain.repository;

import com.himax.ead.course.domain.model.Course;
import com.himax.ead.course.domain.model.CourseUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
public interface CourseUserRepository extends JpaRepository<CourseUser, UUID> {
    boolean existsByCourseAndUserId(Course courseModel, UUID userId);

    @Query(value="select * from tb_courses_users where course_course_id = :courseId", nativeQuery = true)
    List<CourseUser> findAllCourseUserIntoCourse(UUID courseId);

    @Query(value="select * from courses_users " +
                 "where course_id = :courseId " +
                 "  and user_id = :userId", nativeQuery = true)
    Optional<CourseUser> subscriptionExists(UUID courseId, UUID userId);
}