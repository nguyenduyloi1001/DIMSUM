package com.example.DIMSUM.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class MenuItemResponse {
    private Long id;
    private Long categoryId;
    private String categoryName;
    private String name;
    private String description;
    private String cookingStyle;
    private BigDecimal price;
    private String imageUrl;
    private Boolean isAvailable;
    private Boolean isFeatured;
    private LocalDateTime createdAt;
}
