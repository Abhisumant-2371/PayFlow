package com.payflow.user_service.util;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.slf4j.SLF4JLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JWTRequestFilter extends OncePerRequestFilter {
    @Autowired
    private JWTUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authorizationHeader = request.getHeader("Authorization");
        String username = null, jwtToken = null;
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwtToken = authorizationHeader.substring(7);
            try{
                username = jwtUtil.extractUsername(jwtToken);
            }catch (Exception e){
                logger.error(e.getMessage());
            }
        }
        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
             if(jwtUtil.validateToken(jwtToken)) {
                 UsernamePasswordAuthenticationToken authToken =
                         new UsernamePasswordAuthenticationToken(username, null,null);
                 authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                 SecurityContextHolder.getContext().setAuthentication(authToken);
             }
        }

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwtToken = authorizationHeader.substring(7);
            if (jwtToken.isBlank()) {
                filterChain.doFilter(request, response);
                return; // skip processing if token empty
            }
            try {
                username = jwtUtil.extractUsername(jwtToken);
                // only extract role if JWT is valid and present
                String role = jwtUtil.getRole(jwtToken);
                // use role for authorities as needed
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                username,
                                null,
                                List.of(new SimpleGrantedAuthority(role))
                        );

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);


                filterChain.doFilter(request, response);
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        } else {
            filterChain.doFilter(request, response);
        }
    }


}
