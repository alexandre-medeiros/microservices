package com.himax.ead.authuser.domain.services;

import com.himax.ead.authuser.api.v1.model.user.UserFilter;
import com.himax.ead.authuser.api.v1.publishers.UserEventPublisher;
import com.himax.ead.authuser.domain.enums.UserStatus;
import com.himax.ead.authuser.domain.enums.UserType;
import com.himax.ead.authuser.domain.exception.AlreadyExistsException;
import com.himax.ead.authuser.domain.exception.EntityInUseException;
import com.himax.ead.authuser.domain.exception.EntityNotFoundException;
import com.himax.ead.authuser.domain.model.Users;
import com.himax.ead.authuser.domain.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

import static com.himax.ead.authuser.domain.enums.ActionType.CREATE;
import static com.himax.ead.authuser.domain.enums.ActionType.DELETE;
import static com.himax.ead.authuser.domain.enums.ActionType.UPDATE;

@Log4j2
@AllArgsConstructor
@Service
public class UserRegistryService {

    private UserRepository repository;
    private UserEventPublisher userEventPublisher;

    public Page<Users> findAllWithFilter(UserFilter filter, Pageable pageable) {
        UserStatus userStatus = filter.getUserStatus();
        UserType userType = filter.getUserType();
        String email = filter.getEmail();
        String fullName = filter.getFullName();
        return repository.findAllWithFilter(userStatus, userType, email, fullName, pageable);
    }

    public Users find(UUID id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(String.format("User with id %s do not exist", id)));
    }

    @Transactional
    public Users create(Users user) {
        boolean existsSameUserName = repository
                .findByUsername(user.getUsername())
                .filter(u -> !u.equals(user))
                .isPresent();

        if (existsSameUserName) {
            log.warn("Already exist another user with same user name {}}", user.getUsername());
            throw new AlreadyExistsException(String.format("Already exist another user with same user name %s", user.getUsername()));
        }

        boolean existsSameEmail = repository
                .findByEmail(user.getEmail())
                .filter(u -> !u.equals(user))
                .isPresent();

        if (existsSameEmail) {
            log.warn("Already exist another user with same email {}", user.getEmail());
            throw new AlreadyExistsException(String.format("Already exist another user with same email %s", user.getEmail()));
        }

        return repository.save(user);
    }

    @Transactional
    public Users saveAndPublish(Users user) {
        user = create(user);
        userEventPublisher.publishUserEvent(user.toDto(), CREATE);
        return user;
    }

    @Transactional
    public Users update(Users user) {
        return create(user);
    }

    @Transactional
    public Users updateAndPublish(Users user) {
        user = create(user);
        userEventPublisher.publishUserEvent(user.toDto(), UPDATE);
        return user;
    }

    @Transactional
    public Users saveInstructor(UUID userId) {
        Users user = find(userId);
        user.setUserType(UserType.INSTRUCTOR);
        return updateAndPublish(user);
    }

    @Transactional
    public void remove(UUID id) {
        Users user = find(id);

        try {
            repository.deleteById(id);
            userEventPublisher.publishUserEvent(user.toDto(), DELETE);
        } catch (Exception ex) {
            throw new EntityInUseException(String.format("User with id %s can not be deleted because is in use", id));
        }
    }
}
