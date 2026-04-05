package com.example.DIMSUM.service;

import com.example.DIMSUM.request.MenuCategoryRequest;
import com.example.DIMSUM.request.UpdateMenuCategoryRequest;
import com.example.DIMSUM.response.MenuCategoryResponse;

import java.util.List;

public interface MenuCategoryService {
    MenuCategoryResponse createMenuCategory (MenuCategoryRequest request);
    MenuCategoryResponse updateMenuCategoryById(Long categoryId, UpdateMenuCategoryRequest request);
    List<MenuCategoryResponse> getAllMenuCategory();
    MenuCategoryResponse getMenuCategoryById(Long categoryId);
    void deleteCategory(Long categoryId);

    //an hoac hien category
    MenuCategoryResponse toggleActive(Long categoryId);
}
