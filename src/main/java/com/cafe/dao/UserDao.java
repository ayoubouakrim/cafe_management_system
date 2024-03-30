package com.cafe.dao;

import com.cafe.bean.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDao extends JpaRepository<User, Long> {
     Optional<User> findByEmail (String email);


}

