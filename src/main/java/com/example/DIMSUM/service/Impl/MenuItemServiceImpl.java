package com.example.DIMSUM.service.Impl;

import com.example.DIMSUM.model.MenuCategory;
import com.example.DIMSUM.model.MenuItem;
import com.example.DIMSUM.repository.MenuCategoryRepository;
import com.example.DIMSUM.repository.MenuItemRepository;
import com.example.DIMSUM.request.MenuItemRequest;
import com.example.DIMSUM.request.UpdateMenuItemRequest;
import com.example.DIMSUM.response.MenuItemResponse;
import com.example.DIMSUM.service.MenuItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.awt.*;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuItemServiceImpl implements MenuItemService {

    private final MenuItemRepository menuItemRepository;
    private final MenuCategoryRepository menuCategoryRepository;

    @Override
    @Transactional
    public MenuItemResponse createMenuItem(MenuItemRequest request) {
        MenuCategory category = menuCategoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy danh mục với id: " + request.getCategoryId()));

        MenuItem menuItem = MenuItem.builder()
                .menuCategory(category)
                .name(request.getName())
                .description(request.getDescription())
                .cookingStyle(request.getCookingStyle())
                .price(request.getPrice())
                .imageUrl(request.getImageUrl())
                .isAvailable(request.getIsAvailable() != null ? request.getIsAvailable() : true)
                .isFeatured(request.getIsFeatured() != null ? request.getIsFeatured() : false)
                .build();

        MenuItem saved = menuItemRepository.save(menuItem);
        return convertToResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MenuItemResponse> getAllMenuItems() {
        return menuItemRepository.findAll().stream()
                .map(this::convertToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public MenuItemResponse getMenuItemById(Long menuItemId) {
        MenuItem menuItem = menuItemRepository.findById(menuItemId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy món ăn với id: " + menuItemId));
        return convertToResponse(menuItem);
    }

    @Override
    @Transactional
    public MenuItemResponse updateMenuItemById(Long menuItemId, UpdateMenuItemRequest request) {
        MenuItem existing = menuItemRepository.findById(menuItemId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy món ăn với id: " + menuItemId));

        if (request.getCategoryId() != null) {
            MenuCategory category = menuCategoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy danh mục với id: " + request.getCategoryId()));
            existing.setMenuCategory(category);
        }

        if (request.getName() != null) {
            existing.setName(request.getName());
        }

        if (request.getDescription() != null) {
            existing.setDescription(request.getDescription());
        }

        if (request.getCookingStyle() != null) {
            existing.setCookingStyle(request.getCookingStyle());
        }

        if (request.getPrice() != null) {
            existing.setPrice(request.getPrice());
        }

        if (request.getImageUrl() != null) {
            existing.setImageUrl(request.getImageUrl());
        }

        if (request.getIsAvailable() != null) {
            existing.setIsAvailable(request.getIsAvailable());
        }

        if (request.getIsFeatured() != null) {
            existing.setIsFeatured(request.getIsFeatured());
        }

        MenuItem updated = menuItemRepository.save(existing);
        return convertToResponse(updated);
    }

    @Override
    @Transactional
    public void deleteMenuItemById(Long menuItemId) {
        if (!menuItemRepository.existsById(menuItemId)) {
            throw new RuntimeException("Không tìm thấy món ăn với id: " + menuItemId);
        }
        menuItemRepository.deleteById(menuItemId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MenuItemResponse> getMenuItemByCategory(Long categoryId) {
        if (!menuCategoryRepository.existsById(categoryId)) {
            throw new RuntimeException("Không tìm thấy danh mục với ID: " + categoryId);
        }
        return menuItemRepository.findByMenuCategoryId(categoryId).stream()
                .map(this::convertToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<MenuItemResponse> getAvailableMenuItems() {
        return menuItemRepository.findByIsAvailableTrue().stream()
                .map(this::convertToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<MenuItemResponse> getFeaturedMenuItems() {
        return menuItemRepository.findByIsFeaturedTrue().stream()
                .map(this::convertToResponse)
                .toList();
    }

    @Override
    @Transactional
    public MenuItemResponse toggleAvailability(Long menuItemId) {
        MenuItem menuItem = menuItemRepository.findById(menuItemId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy món ăn với id: " + menuItemId));

        menuItem.setIsAvailable(!menuItem.getIsAvailable());
        MenuItem updated = menuItemRepository.save(menuItem);
        return convertToResponse(updated);
    }

    @Override
    @Transactional
    public MenuItemResponse toggleFeatured(Long menuItemId) {
        MenuItem menuItem = menuItemRepository.findById(menuItemId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy món ăn với id: " + menuItemId));

        menuItem.setIsFeatured(!menuItem.getIsFeatured());
        MenuItem updated = menuItemRepository.save(menuItem);
        return convertToResponse(updated);
    }

    private MenuItemResponse convertToResponse(MenuItem item) {
        return MenuItemResponse.builder()
                .id(item.getId())
                .categoryId(item.getMenuCategory() != null ? item.getMenuCategory().getId() : null)
                .categoryName(item.getMenuCategory() != null ? item.getMenuCategory().getName() : null)
                .name(item.getName())
                .description(item.getDescription())
                .cookingStyle(item.getCookingStyle())
                .price(item.getPrice())
                .imageUrl(item.getImageUrl())
                .isAvailable(item.getIsAvailable())
                .isFeatured(item.getIsFeatured())
                .createdAt(item.getCreatedAt())
                .build();
    }
}
