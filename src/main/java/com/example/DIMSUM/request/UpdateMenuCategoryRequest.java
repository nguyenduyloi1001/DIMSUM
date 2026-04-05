package com.example.DIMSUM.request;

import jakarta.validation.constraints.Min;
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
public class UpdateMenuCategoryRequest {

    @Size(min = 2, max = 50, message = "Tên danh mục phải từ 2-50 ký tự")
    private String name;

    @Pattern(regexp = "^https?://.*", message = "Icon phải là URL hợp lệ")
    private String icon;

    @Min(value = 0, message = "Thứ tự không được âm")
    private Integer sortOrder;

    private Boolean isActive;
}
