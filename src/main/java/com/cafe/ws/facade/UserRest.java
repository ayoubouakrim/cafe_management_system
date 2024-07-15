package com.cafe.ws.facade;

import com.cafe.bean.User;
import com.cafe.config.AuthenticationResponse;
import com.cafe.service.facade.UserService;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping(path="/user")
public class UserRest {
    private final UserService userService;

    public UserRest(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<AuthenticationResponse> signup(@RequestBody User request) {
        return  ResponseEntity.ok(userService.signup(request));
    }
    @PostMapping("/login")
    public  ResponseEntity<AuthenticationResponse> login(@RequestBody User request) {
        return  ResponseEntity.ok(userService.authenticate(request));
    }


@GetMapping("/all")
    public ResponseEntity<List<User>> findAll(){
        try {
            return userService.findAll();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<List<User>>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
