package com.example.DIMSUM.service;

import com.example.DIMSUM.request.MenuItemRequest;
import com.example.DIMSUM.request.UpdateMenuItemRequest;
import com.example.DIMSUM.response.MenuItemResponse;

import java.util.List;

public interface MenuItemService {
    MenuItemResponse createMenuItem(MenuItemRequest request);

    List<MenuItemResponse> getAllMenuItems();

    MenuItemResponse getMenuItemById(Long menuItemId);

    MenuItemResponse updateMenuItemById(Long menuItemId, UpdateMenuItemRequest request);

    void deleteMenuItemById(Long menuItemId);

    List<MenuItemResponse> getMenuItemByCategory(Long categoryId);

    List<MenuItemResponse> getAvailableMenuItems();

    List<MenuItemResponse> getFeaturedMenuItems();

    MenuItemResponse toggleAvailability(Long menuItemId);

    MenuItemResponse toggleFeatured(Long menuItemId);
}