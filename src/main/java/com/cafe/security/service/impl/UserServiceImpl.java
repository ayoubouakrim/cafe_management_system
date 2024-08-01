package com.cafe.security.service.impl;


import com.cafe.security.dao.UserDao;
import com.cafe.security.service.facade.RoleService;
import com.cafe.security.service.facade.UserService;
import com.cafe.security.bean.Role;
import com.cafe.security.bean.User;
import com.cafe.security.ws.dto.request.PasswordAndEmailChange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
public class UserServiceImpl implements UserService {
    private final UserDao userDao;
    private final RoleService roleService;
    @Lazy
    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;

    public UserServiceImpl(UserDao userDao, RoleService roleService) {
        this.userDao = userDao;
        this.roleService = roleService;
    }

    @Override
    public String cryptPassword(String value) {
        return value == null ? null : bCryptPasswordEncoder.encode(value);
    }

    @Override
    public boolean changePassword(String username, String newPassword) {
        User user = userDao.findByUsername(username);
        if (user != null) {
            user.setPassword(cryptPassword(newPassword));
            user.setPasswordChanged(true);
            userDao.save(user);
            return true;
        }
        return false;
    }


    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    public User findByUsername(String username) {
        if (username == null)
            return null;
        return userDao.findByUsername(username);
    }

    @Override
    public User findByUsernameWithRoles(String username) {
        if (username == null)
            return null;
        return userDao.findByUsername(username);
    }

    @Override
    @Transactional
    public int deleteByUsername(String username) {
        return userDao.deleteByUsername(username);
    }

    @Override
    public User findById(Long id) {
        return userDao.findById(id).orElse(null);
    }

    @Transactional
    public void deleteById(Long id) {
        userDao.deleteById(id);
    }

    @Override
    public User save(User user) {
        User foundedUserByUsername = findByUsername(user.getUsername());
        User foundedUserByEmail = userDao.findByEmail(user.getEmail());
        if (foundedUserByUsername != null || foundedUserByEmail != null) return null;
        else {
            if (user.getPassword() == null || user.getPassword().isEmpty()) {
                user.setPassword(bCryptPasswordEncoder.encode(user.getUsername()));
            } else {
                user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            }
            user.setAccountNonExpired(true);
            user.setAccountNonLocked(true);
            user.setCredentialsNonExpired(true);
            user.setEnabled(true);
            user.setPasswordChanged(false);
            user.setCreatedOn(LocalDateTime.now());


            List<Role> roles = new ArrayList<>();
            Role userRole = roleService.findByName("USER");
            roles.add(userRole);
            user.setRoles(roles);
        /*  ----------------------
            else {
                List<Role> roles = new ArrayList<>();
                for (Role role : user.getRoles()) {
                    roles.add(roleService.save(role));
                }
                user.setRoles(roles);
                  }
        ------------------------- */
        }
        return userDao.save(user);
    }



@Override
public User update(User user) {
    User foundedUser = findById(user.getId());
    if (foundedUser == null) return null;
    else {
        foundedUser.setEmail(user.getEmail());
        foundedUser.setUsername(user.getUsername());
        foundedUser.setEnabled(user.isEnabled());
        foundedUser.setCredentialsNonExpired(user.isCredentialsNonExpired());
        foundedUser.setAccountNonLocked(user.isAccountNonLocked());
        foundedUser.setAccountNonExpired(user.isAccountNonExpired());
        foundedUser.setAuthorities(new ArrayList<>());
        List<Role> roles = new ArrayList<>();
        for (Role role : user.getRoles()) {
            roles.add(roleService.save(role));
        }
        foundedUser.setRoles(roles);
        return userDao.save(foundedUser);
    }
}

@Override
@Transactional
public int delete(User user) {
    if (user == null || user.getId() == null) return 0;
    User foundedUser = findById(user.getId());
    if (foundedUser == null) return -1;
    userDao.delete(foundedUser);
    return 1;
}

@Override
public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return findByUsernameWithRoles(username);
}

@Override
public User findByEmail(String email) {
    return userDao.findByEmail(email);
}

@Override
@Transactional
public void updateUsernamePasswordAndEmail(PasswordAndEmailChange passwordAndEmailChange) {
    Long userId = passwordAndEmailChange.getId();
    String username = passwordAndEmailChange.getUsername();
    String newPassword = passwordAndEmailChange.getPassword();
    String newEmail = passwordAndEmailChange.getEmail();
    if (!username.isEmpty() && !newEmail.isEmpty() && userId != null) {
        if (!newPassword.isEmpty()) {
            String newPasswordEncoder = bCryptPasswordEncoder.encode(newPassword);
            userDao.updatePasswordEmailAndUsername(username, newPasswordEncoder, newEmail, userId);
        } else {
            userDao.updateEmailAndUsername(username, newEmail, userId);
        }
    }
}

@Override
@Transactional
public void updatePassword(PasswordAndEmailChange passwordAndEmailChange) {
    Long userId = passwordAndEmailChange.getId();
    String newPassword = passwordAndEmailChange.getPassword();
    if (!newPassword.isEmpty() && userId != null) {
        String newPasswordEncoder = bCryptPasswordEncoder.encode(newPassword);
        userDao.updatePassword(newPasswordEncoder, userId);
    }
}
}
