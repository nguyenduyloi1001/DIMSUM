package com.example.DIMSUM.request;

import com.example.DIMSUM.enums.TableStatus;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateDiningTableRequest {
    @Size(min = 1, max = 20, message = "Số bàn phải từ 1-20 ký tự")
    @Pattern(regexp = "^[A-Za-z0-9\\s-]+$", message = "Số bàn chỉ được chứa chữ, số, dấu cách và dấu gạch ngang")
    private String tableNumber;

    @Min(value = 1, message = "Sức chứa tối thiểu là 1 người")
    @Max(value = 20, message = "Sức chứa tối đa là 20 người")
    private Integer capacity;

    private TableStatus status;  // Không cần validate vì enum tự check

    @AssertTrue(message = "Phải có ít nhất một trường để cập nhật")
    private boolean isValid() {
        return tableNumber != null || capacity != null || status != null;
    }
}
