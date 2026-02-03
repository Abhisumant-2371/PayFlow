package com.payflow.user_service.service;

import com.payflow.user_service.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User createUser(User user);

    Optional<User> findById(Long id);

    List<User> getAllUsers();

    public Optional<User> getUserByEmail(String email);

    public User saveUser(User user);

}
