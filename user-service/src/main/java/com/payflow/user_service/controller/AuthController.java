package com.payflow.user_service.controller;

import com.payflow.user_service.dto.JwtTokenResponse;
import com.payflow.user_service.dto.LoginRequest;
import com.payflow.user_service.dto.SignUpRequest;
import com.payflow.user_service.entity.User;
import com.payflow.user_service.service.UserService;
import com.payflow.user_service.util.JWTUtil;
import com.payflow.user_service.util.UserUtil;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);
    @Autowired
    private UserService userService;

    @Autowired
    private UserUtil userUtil;

    @Autowired
    private JWTUtil jwtUtil;

    public AuthController() {
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody SignUpRequest signUpRequest) {
        if(signUpRequest.getEmail() == null || signUpRequest.getEmail().isEmpty()){
                log.warn("Email is empty");
                return ResponseEntity.badRequest().body("Email is empty");
        }
        String email = signUpRequest.getEmail();
        Optional<User> user= userService.getUserByEmail(email);

        if(user.isPresent()){
            log.warn("User with email {} already exists", email);
            return ResponseEntity.badRequest().body("User with email already exists");
        }
        User newUser = userUtil.getUserFromSignUpRequest(signUpRequest);

        userService.saveUser(newUser);

        return new ResponseEntity<>("User created successfully", HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {

        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();
        Optional<User> user= userService.getUserByEmail(email);
        if(user.isEmpty()){
            return new ResponseEntity<>("User not found", HttpStatus.UNAUTHORIZED);
        }
        if(!userUtil.isPasswordMatching(password,user.get().getPassword())){
            return new ResponseEntity<>("Incorrect password", HttpStatus.UNAUTHORIZED);
        }

        Map<String, Object> claims = new HashMap<>();
        claims.put("role",user.get().getRole());
        String token = jwtUtil.generateToken(claims,user.get().getEmail());
        return new ResponseEntity<>(new JwtTokenResponse(token),HttpStatus.OK);
    }
}
