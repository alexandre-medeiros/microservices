package com.himax.ead.course.domain.repository;

import com.himax.ead.course.domain.model.Modules;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ModuleRepository extends JpaRepository<Modules, UUID> {

    @EntityGraph(attributePaths = "course")
    Optional<Modules> findByTitle(String title);

    @Query(value = "select * from tb_modules where course_id = :courseId", nativeQuery = true)
    List<Modules> findAllLModulesIntoCourse(@Param("courseId") UUID courseId);

    @Query(value = "select * from tb_modules where course_id = :courseId and module_id = :moduleId", nativeQuery = true)
    Optional<Modules> findModuleIntoCourse(@Param("courseId") UUID courseId, @Param("moduleId") UUID moduleId);

    @Modifying
    @Query(value = "delete from modules where id in " +
            "(select m.id id " +
            " from modules m, courses c " +
            " where c.id = m.course_id " +
            "   and c.id = :courseId)", nativeQuery = true)
    void deleteModuleByCourseId(UUID courseId);
}
