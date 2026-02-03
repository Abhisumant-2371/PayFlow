package com.payflow.user_service.util;

import com.payflow.user_service.dto.SignUpRequest;
import com.payflow.user_service.entity.SystemRole;
import com.payflow.user_service.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserUtil {


    private final PasswordEncoder passwordEncoder;


    public UserUtil(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public User getUserFromSignUpRequest(SignUpRequest signUpRequest) {
        User user = new User();
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        user.setName(signUpRequest.getName());
        user.setRole(SystemRole.ROLE_USER.name());
        return user;

    }

    public boolean isPasswordMatching(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
