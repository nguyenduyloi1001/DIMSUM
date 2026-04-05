package com.example.DIMSUM.repository;

import com.example.DIMSUM.model.MenuCategory;
import com.example.DIMSUM.model.User;
import org.springframework.data.jpa.repository.JpaRepository;



public interface MenuCategoryRepository extends JpaRepository<MenuCategory, Long> {
    boolean existsByName(String name);

    boolean existsById(Long categoryId);

    void deleteById(Long categoryId);
}
