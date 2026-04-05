package com.example.DIMSUM.request;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class MenuItemRequest {

    @NotNull(message = "Category ID không được để trống")
    private Long categoryId;

    @NotBlank(message = "Tên món không được để trống")
    @Size(min=2,max=100,message = "Tên móng phải từ 2 - 100 ký tự")
    private String name;

    @Size(max=500, message = "Mô tả không quá 500 ký tự")
    private String description;

    @Pattern(regexp = "^(kho|nuoc|hap|chien|hap)$", message = "Cooking style không hợp lệ")
    private String cookingStyle;

    @NotNull(message = "Giá không được để trống")
    @DecimalMin(value = "0" ,inclusive = false,message = "Giá phải lớn hơn 0")
    @DecimalMax(value = "999999999",message = "Giá không được vượt quá 9,999,999")
    private BigDecimal price;

    @Pattern(regexp = "^(http://|https://|/).*", message = "URL ảnh không hợp lệ")
    private String imageUrl;

    private Boolean isAvailable;

    // mon an noi bat
    private Boolean isFeatured;
}
