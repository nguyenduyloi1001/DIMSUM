package com.example.DIMSUM.service;

import com.example.DIMSUM.model.User;
import com.example.DIMSUM.request.UpdateUserRequest;
import com.example.DIMSUM.request.UserRequest;
import com.example.DIMSUM.response.UserResponse;

import java.util.List;

public interface UserService {
    UserResponse createUser(UserRequest request);
    List<UserResponse> getAllUser();
    UserResponse findUserById(Long id);
    UserResponse updateUserById(Long id, UpdateUserRequest request);
    void deleteUserById(Long id);
}
