package com.himax.ead.authuser.domain.services;

import com.himax.ead.authuser.domain.model.Users;
import com.himax.ead.authuser.domain.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.UUID;

@AllArgsConstructor
@Service
public class AuthDetailsService implements UserDetailsService {

    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Users user = repository
                .loadUserByUsername(userName)
                .orElseThrow(() ->
                        new UsernameNotFoundException(String.format("User with userName %s do not exist", userName)));
        return user.toUserDetails();
    }

    public UserDetails loadUserByUserId(UUID id) throws UsernameNotFoundException {
        Users user = repository
                .loadUserById(id)
                .orElseThrow(() ->
                        new UsernameNotFoundException(String.format("User with userName %s do not exist", id)));
        return user.toUserDetails();
    }
}
