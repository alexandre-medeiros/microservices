package com.himax.ead.course.domain.repository;

import com.himax.ead.course.domain.model.Users;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<Users, UUID> {

    @Query("select U from Users U JOIN U.courses C " +
            "where (cast(:courseId as org.hibernate.type.UUIDCharType) IS NULL OR C.id = :courseId)" +
            "  and (:userStatus is null or U.userStatus = :userStatus)" +
            "  and (:userType is null or U.userType = :userType)" +
            "  and (:email is null or U.email like (%:email%))" +
            "  and (:fullName is null or U.fullName like (%:fullName%))")
    List<Users> findAllWithFilter(@Param("userStatus") String userStatus,
                                  @Param("userType") String userType,
                                  @Param("email") String email,
                                  @Param("fullName") String fullName,
                                  @Param("courseId") UUID courseId,
                                  Pageable pageable);

    @Modifying
    @Query(value = "delete from courses_users where courses_id = :courseId", nativeQuery = true)
    void deleteCourseUserByCourseId(UUID courseId);

    @Modifying
    @Query(value = "delete from courses_users where users_id = :userId", nativeQuery = true)
    void deleteCourseUsersByUserId(UUID userId);
}
