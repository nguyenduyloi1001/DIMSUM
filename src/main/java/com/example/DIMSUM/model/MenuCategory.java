package com.example.DIMSUM.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "menu_categories")
@Builder

public class MenuCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String icon;

    //Danh mục còn dùng hay tạm ẩn.
    private Integer sortOrder=0;

    //Danh mục còn dùng hay tạm ẩn.
    private Boolean isActive = true;

    //Một danh mục có nhiều món.
    @OneToMany(mappedBy = "menuCategory")
    private List<MenuItem> menuItems;
}
