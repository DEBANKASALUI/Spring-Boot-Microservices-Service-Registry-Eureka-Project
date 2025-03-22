package com.user.service;

import com.user.entity.User;

import java.util.List;

public interface UserService {

    User saveUser(User user);

    List<User> getAllUsers();

    User getUserById(String userId);

    User updateUserById(String id, User user);

    void deleteUserByID(String id);

}
