package com.example.DIMSUM.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateMenuItemRequest {
    private Long categoryId;

    @Size(min = 2, max = 100, message = "Tên món phải từ 2 - 100 ký tự")
    private String name;

    @Size(max = 500, message = "Mô tả không quá 500 ký tự")
    private String description;

    @Pattern(regexp = "^(kho|nuoc|hap|chien)$", message = "Cooking style không hợp lệ")
    private String cookingStyle;

    @DecimalMin(value = "0", inclusive = false, message = "Giá phải lớn hơn 0")
    @DecimalMax(value = "999999999", message = "Giá không được vượt quá 999,999,999")
    private BigDecimal price;

    @Pattern(regexp = "^(http://|https://|/).*", message = "URL ảnh không hợp lệ")
    private String imageUrl;

    private Boolean isAvailable;

    private Boolean isFeatured;
}
