package com.himax.ead.course.domain.repository;

import com.himax.ead.course.domain.enums.CourseLevel;
import com.himax.ead.course.domain.enums.CourseStatus;
import com.himax.ead.course.domain.model.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface CourseRepository extends JpaRepository<Course, UUID> {

    @Query("select C from Course C LEFT JOIN C.users U " +
            "where (cast(:userId as org.hibernate.type.UUIDCharType) IS NULL OR U.id = :userId)" +
            "  and (:courseLevel IS NULL  or C.courseLevel = :courseLevel)" +
            "  and (:courseStatus IS NULL  or C.courseStatus = :courseStatus)" +
            "  and (:name IS NULL  or C.name like (%:name%))")
    Page<Course> findAllWithFilter(@Param("courseLevel") CourseLevel courseLevel,
                                   @Param("courseStatus") CourseStatus courseStatus,
                                   @Param("name") String name,
                                   @Param("userId") UUID userId,
                                   Pageable pageable);
}
