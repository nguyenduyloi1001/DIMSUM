package com.example.DIMSUM.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserRequest {
    @Size(min = 6, message = "Password phải có ít nhất 6 ký tự")
    private String password;
    private String fullName;

    @Pattern(regexp = "^[0-9]{10}$", message = "Số điện thoại phải là 10 chữ số")
    private String phone;

    @Email(message = "Email không đúng định dạng")
    private String email;
}
