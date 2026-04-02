package com.example.DIMSUM.controller;

import com.example.DIMSUM.model.User;
import com.example.DIMSUM.request.UpdateUserRequest;
import com.example.DIMSUM.request.UserRequest;
import com.example.DIMSUM.response.ApiResponse;
import com.example.DIMSUM.response.UserResponse;
import com.example.DIMSUM.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ApiResponse<UserResponse> createUser(@RequestBody @Valid UserRequest request){
        UserResponse user = userService.createUser(request);
        return ApiResponse.<UserResponse>builder()
                .code(1000)
                .message("Tạo thành công User")
                .result(user)
                .build();
    }

    @GetMapping
    ApiResponse<List<UserResponse>> getAllUser(){
        List<UserResponse> user = userService.getAllUser();
        return ApiResponse.<List<UserResponse>>builder()
                .code(1000)
                .message("Lấy danh sách thành công")
                .result(user) //result chứa
                .build();
    }

    @GetMapping("/{userId}")
    ApiResponse<UserResponse> getUserById(@PathVariable Long userId)
    {
        UserResponse user = userService.findUserById(userId);
        return ApiResponse.<UserResponse>builder()
                .code(1000)
                .message("Lấy thông tin User thành công")
                .result(user)
                .build();
    }

    @PutMapping("/{userId}")
    ApiResponse<UserResponse> updateUsersById(@PathVariable Long userId,
                                              @RequestBody UpdateUserRequest request){
        UserResponse user = userService.updateUserById(userId,request);
        return ApiResponse.<UserResponse>builder()
                .code(1000)
                .message("Cập nhập User thành công")
                .result(user)
                .build();
    }

    @DeleteMapping("/{userId}")
    public ApiResponse<Void> deleteUserById(@PathVariable Long userId) {
        userService.deleteUserById(userId);
        return ApiResponse.<Void>builder()
                .code(1000)
                .message("Xóa User thành công")
                .result(null)
                .build();
    }

}
