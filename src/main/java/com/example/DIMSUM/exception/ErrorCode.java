package com.example.DIMSUM.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    USER_NOT_FOUND(1001, "Không tìm thấy người dùng"),
    USERNAME_EXISTED(1002, "Username đã tồn tại"),
    EMAIL_EXISTED(1003, "Email đã tồn tại"),
    UNAUTHORIZED(1004, "Không có quyền truy cập");

    private final int code;
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}