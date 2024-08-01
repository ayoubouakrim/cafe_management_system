package com.cafe.security.jwt;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.cafe.security.utils.SecurityParams.*;


@Component
public class AuthTokenFilter extends OncePerRequestFilter {
    @Value("${app.frontend}")
    private String origins;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if (!response.containsHeader("Access-Control-Allow-Origin")) {
            response.addHeader("Access-Control-Allow-Origin", origins);
        } else {
            response.setHeader("Access-Control-Allow-Origin", origins);
        }

        response.addHeader("Access-Control-Allow-Headers", "Origin, Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers, Authorization");
        response.addHeader("Access-Control-Allow-Methods", "DELETE, POST, GET, PUT, PATCH, OPTIONS");
        response.addHeader("Access-Control-Expose-Headers", "Access-Control-Allow-Origin, Access-Control-Allow-Credentials, Authorization");

        if (request.getMethod().equals("OPTIONS")) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else if (request.getRequestURI().equals("/v1/login")) {
            filterChain.doFilter(request, response);
        } else {
            String jwtToken = request.getHeader(JWT_HEADER_NAME);
            if ((jwtToken == null) || !jwtToken.startsWith(HEADER_PREFIX)) {
                filterChain.doFilter(request, response);
                return;
            }
            setAuth(jwtToken);
            filterChain.doFilter(request, response);
        }
    }

    private void setAuth(String jwtToken) {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
        String jwt = jwtToken.substring(HEADER_PREFIX.length());
        DecodedJWT decodedJWT = verifier.verify(jwt);
        String username = decodedJWT.getSubject();
        List<String> roles = decodedJWT.getClaims().get("roles").asList(String.class);
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        roles.forEach(rn -> authorities.add(new SimpleGrantedAuthority(rn)));
        UsernamePasswordAuthenticationToken user = new UsernamePasswordAuthenticationToken(username, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(user);
    }

    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }
        return null;
    }
}

