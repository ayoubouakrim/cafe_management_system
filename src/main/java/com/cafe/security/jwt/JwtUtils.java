package com.cafe.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.cafe.security.bean.User;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import static com.cafe.security.utils.SecurityParams.EXPIRATION;
import static com.cafe.security.utils.SecurityParams.SECRET;


@Component
public class JwtUtils {
    public String generateJwtToken(Authentication authentication) {

        User userPrincipal = (User) authentication.getPrincipal();
        Collection<String> roles = new ArrayList<>();
        if (userPrincipal.getAuthorities() != null) {
            userPrincipal.getAuthorities().forEach(a -> roles.add(a.getAuthority()));
        }

        return JWT.create()
                .withSubject(userPrincipal.getUsername())
                .withArrayClaim("roles", roles.toArray(new String[roles.size()]))
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION))
                .sign(Algorithm.HMAC256(SECRET));
    }
}
