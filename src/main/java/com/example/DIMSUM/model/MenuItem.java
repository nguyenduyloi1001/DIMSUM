package com.example.DIMSUM.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "menu_items")
@Builder

public class MenuItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private MenuCategory menuCategory;

    @Column(nullable = false)
    private String name;

    private String description;

    // THÊM: "kho", "nuoc", "hap", null (nếu không phải món Chính)
    private String cookingStyle;

    @Column(nullable = false)
    private BigDecimal price;

    private String imageUrl;

    private  Boolean isAvailable = true;

    private Boolean isFeatured = false;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
