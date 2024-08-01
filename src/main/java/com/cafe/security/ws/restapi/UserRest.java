package com.cafe.security.ws.restapi;


import com.cafe.security.bean.User;
import com.cafe.security.service.facade.UserService;
import com.cafe.security.ws.dto.UserDto;
import com.cafe.security.ws.converter.UserConverter;
import com.cafe.security.ws.dto.request.PasswordAndEmailChange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/user")
@RestController
public class UserRest {
    @Autowired
    private UserService userService;
    @Autowired
    private UserConverter userConverter;

    public UserRest(UserService userService, UserConverter userConverter) {
        this.userService = userService;
        this.userConverter = userConverter;
    }

    @GetMapping()
    public List<UserDto> findAll() {
        List<User> all = this.userService.findAll();
        return userConverter.toDto(all);
    }

    public User findByUsername(String username) {
        return userService.findByUsername(username);
    }

    @GetMapping("/id/{id}")
    public UserDto findById(@PathVariable Long id) {
        User byId = userService.findById(id);
        return userConverter.toDto(byId);
    }

    @DeleteMapping("/id/{id}")
    public void deleteById(@PathVariable Long id) {
        userService.deleteById(id);
    }

    @PostMapping("/register")
    public UserDto save(@RequestBody UserDto userDto) {
        User user = userConverter.toItem(userDto);
        User saved = userService.save(user);
        return userConverter.toDto(saved);
    }

    @PutMapping("/update")
    public void updateUsernamePasswordAndEmail(@RequestBody PasswordAndEmailChange passwordAndEmailChange) {
        userService.updateUsernamePasswordAndEmail(passwordAndEmailChange);
    }

    @DeleteMapping()
    public int delete(@RequestBody UserDto userDto) {
        User user = userConverter.toItem(userDto);
        return userService.delete(user);
    }

    @GetMapping("/username/{username}")
    public UserDto findByUsernameWithRoles(@PathVariable String username) {
        return userConverter.toDto(userService.findByUsernameWithRoles(username));
    }

    @DeleteMapping("/username/{username}")
    public int deleteByUsername(@PathVariable String username) {
        return userService.deleteByUsername(username);
    }

    @PutMapping("/password/update")
    public void updatePassword(@RequestBody PasswordAndEmailChange passwordAndEmailChange) {
        userService.updatePassword(passwordAndEmailChange);
    }
}

