package com.cafe.service.impl;

import com.cafe.bean.User;
import com.cafe.config.AuthenticationResponse;
import com.cafe.dao.UserDao;
import com.cafe.service.facade.UserService;
import com.cafe.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserServiceImpl implements UserService {


    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    @Autowired @Lazy
    private AuthenticationManager authenticationManager;

    public UserServiceImpl(UserDao userDao, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;

    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userDao.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
    @Override
    public AuthenticationResponse signup(User request){
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setAuthorities(request.getAuthorities());
        user = userDao.save(user);
        String token = jwtUtil.generateToken(user);

        return new AuthenticationResponse(token);

    }
    @Override

    public AuthenticationResponse authenticate(User request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        User user = userDao.findByEmail(request.getEmail()).orElseThrow();

        String token = jwtUtil.generateToken(user);
        return new AuthenticationResponse(token);
    }

    public ResponseEntity<List<User>> findAll() {
        return (ResponseEntity<List<User>>) userDao.findAll();
    }
}
