package com.example.DIMSUM.repository;

import com.example.DIMSUM.model.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.*;
import java.util.List;

public interface MenuItemRepository extends JpaRepository<MenuItem,Long> {
    List<MenuItem> findByMenuCategoryId(Long categoryId);
    List<MenuItem> findByIsFeaturedTrue();
    List<MenuItem> findByIsAvailableTrue();

    boolean existsByMenuCategoryId(Long categoryId);
}
