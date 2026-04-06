package com.example.DIMSUM.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DiningTableRequest {
    @NotBlank(message = "Số bàn không được để trống")
    @Size(min = 1, max = 20, message = "Số bàn phải từ 1-20 ký tự")
    @Pattern(regexp = "^[A-Za-z0-9\\s-]+$", message = "Số bàn chỉ được chứa chữ, số, dấu cách và dấu gạch ngang")
    private String tableNumber;

    @NotNull(message = "Sức chứa không được để trống")
    @Min(value = 1, message = "Sức chứa tối thiểu là 1 người")
    @Max(value = 20, message = "Sức chứa tối đa là 20 người")
    private Integer capacity;
}
