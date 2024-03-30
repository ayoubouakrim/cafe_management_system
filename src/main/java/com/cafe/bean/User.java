package com.cafe.bean;

import com.cafe.bean.Role;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
@Data
@Entity
public class User implements UserDetails {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String username;
    private String email;
    private String password;
    @ManyToMany
    private Collection<Role> authorities;
    private boolean accountNonExpired=true;
    private boolean accountNonLocked=true;
    private boolean credentialsNonExpired=true;
    private boolean enabled=true;
    private boolean status=false;




}