package com.himax.ead.course.domain.repository;

import com.himax.ead.course.domain.model.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LessonRepository extends JpaRepository<Lesson, UUID> {

    @Query(value = "select * from tb_lessons where module_id = :moduleId", nativeQuery = true)
    List<Lesson> findAllLessonsIntoModule(@Param("moduleId") UUID moduleId);

    @Query(value = "select * from tb_lessons where module_id = :moduleId and id = :lessonId", nativeQuery = true)
    Optional<Lesson> findLessonIntoModule(@Param("moduleId") UUID moduleId, @Param("lessonId") UUID lessonId);

    @Modifying
    @Query(value = "delete from lessons where id in " +
            "(select l.id id " +
            " from lessons l, modules m, courses c " +
            " where c.id = m.course_id " +
            "  and m.id = l.module_id " +
            "  and c.id = :courseId)", nativeQuery = true)
    void deleteLessonByCourseId(UUID courseId);


}
