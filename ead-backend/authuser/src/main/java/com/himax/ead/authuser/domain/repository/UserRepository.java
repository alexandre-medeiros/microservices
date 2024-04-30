package com.himax.ead.authuser.domain.repository;

import com.himax.ead.authuser.domain.enums.UserStatus;
import com.himax.ead.authuser.domain.enums.UserType;
import com.himax.ead.authuser.domain.model.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<Users, UUID> {

    Optional<Users> findByEmail(String email);

    Optional<Users> findByUsername(String username);

    @Query("select distinct U from Users U " +
            "where (:userStatus is null or U.userStatus = :userStatus)" +
            "  and (:userType is null or U.userType = :userType)" +
            "  and (:email is null or U.email like (%:email%))" +
            "  and (:fullName is null or U.fullName like (%:fullName%))")
    Page<Users> findAllWithFilter(@Param("userStatus") UserStatus userStatus,
                                  @Param("userType") UserType userType,
                                  @Param("email") String email,
                                  @Param("fullName") String fullName,
                                  Pageable pageable);

    @Query("SELECT U FROM Users U join fetch U.roles WHERE U.username = :username")
    Optional<Users> loadUserByUsername(String username);
}
