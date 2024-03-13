package com.himax.ead.course.domain.repository;

import com.himax.ead.course.domain.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface CourseRepository extends JpaRepository<Course, UUID> {
}
