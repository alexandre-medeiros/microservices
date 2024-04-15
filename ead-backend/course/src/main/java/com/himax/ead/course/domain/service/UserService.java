package com.himax.ead.course.domain.service;

import com.himax.ead.course.api.v1.model.UserFilter;
import com.himax.ead.course.domain.exception.EntityNotFoundException;
import com.himax.ead.course.domain.model.Users;
import com.himax.ead.course.domain.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Service
public class UserService {

    private UserRepository repository;

    public List<Users> findAllWithFilter(UserFilter filter, UUID courseId, Pageable pageable) {
        String userStatus = filter.getUserStatus();
        String userType = filter.getUserType();
        String email = filter.getEmail();
        String fullName = filter.getFullName();
        return repository.findAllWithFilter(userStatus, userType, email, fullName, courseId, pageable);
    }

    public Users findById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id " + id));
    }

    @Transactional
    public void create(Users user) {
        repository.save(user);
    }

    @Transactional
    public void update(Users user) {
        Users userExisting = repository.findById(user.getId())
                .orElseThrow(() -> new EntityNotFoundException("User not exist with id " + user.getId()));

        BeanUtils.copyProperties(user, userExisting);
        create(userExisting);
    }

    @Transactional
    public void delete(Users user) {
        repository.deleteCourseUsersByUserId(user.getId());
        repository.delete(user);
    }

    @Transactional
    public void deleteUsersByCourseId(UUID id) {
        repository.deleteCourseUserByCourseId(id);
    }
}
