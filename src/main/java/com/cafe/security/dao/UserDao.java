package com.cafe.security.dao;


import com.cafe.security.bean.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserDao extends JpaRepository<User, Long> {
    User findByUsername(String username);

    int deleteByUsername(String username);

    User findByEmail(String email);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.username = :newUsername, u.password = :newPassword, u.email = :newEmail WHERE u.id = :userId")
    void updatePasswordEmailAndUsername(@Param("newUsername") String newUsername, @Param("newPassword") String newPassword, @Param("newEmail") String newEmail, @Param("userId") Long userId);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.username = :newUsername, u.email = :newEmail WHERE u.id = :userId")
    void updateEmailAndUsername(@Param("newUsername") String newUsername, @Param("newEmail") String newEmail, @Param("userId") Long userId);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.password = :newPassword WHERE u.id = :userId")
    void updatePassword(@Param("newPassword") String newPassword, @Param("userId") Long userId);
}
