package com.cafe.security.service.facade;


import com.cafe.security.bean.User;
import com.cafe.security.ws.dto.request.PasswordAndEmailChange;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserService extends UserDetailsService {

    String cryptPassword(String value);

    boolean changePassword(String username, String newPassword);

    List<User> findAll();

    User findByUsername(String username);

    User findById(Long id);

    void deleteById(Long id);

    User save(User user);

    User update(User user);

    int delete(User user);

    User findByUsernameWithRoles(String username);

    int deleteByUsername(String username);

    UserDetails loadUserByUsername(String username);

    User findByEmail(String email);

    @Transactional
    void updateUsernamePasswordAndEmail(PasswordAndEmailChange passwordAndEmailChange);


    @Transactional
    void updatePassword(PasswordAndEmailChange passwordAndEmailChange);
}
