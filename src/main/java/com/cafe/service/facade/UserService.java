package com.cafe.service.facade;

import com.cafe.bean.User;
import com.cafe.config.AuthenticationResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface UserService extends UserDetailsService {
    UserDetails loadUserByUsername(String email) throws UsernameNotFoundException;

    AuthenticationResponse signup(User request);

    AuthenticationResponse authenticate(User request);

    ResponseEntity<List<User>> findAll();
}
