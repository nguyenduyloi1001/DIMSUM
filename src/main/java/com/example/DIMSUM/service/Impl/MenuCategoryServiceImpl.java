package com.example.DIMSUM.service.Impl;

import com.example.DIMSUM.model.MenuCategory;
import com.example.DIMSUM.model.MenuItem;
import com.example.DIMSUM.repository.MenuCategoryRepository;
import com.example.DIMSUM.repository.MenuItemRepository;
import com.example.DIMSUM.request.MenuCategoryRequest;
import com.example.DIMSUM.request.UpdateMenuCategoryRequest;
import com.example.DIMSUM.response.MenuCategoryResponse;
import com.example.DIMSUM.response.MenuItemResponse;
import com.example.DIMSUM.service.MenuCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MenuCategoryServiceImpl implements MenuCategoryService {

    private final MenuCategoryRepository menuCategoryRepository;
    private final MenuItemRepository menuItemRepository;
    @Override
    public MenuCategoryResponse createMenuCategory(MenuCategoryRequest request) {
        if (menuCategoryRepository.existsByName(request.getName())) {
            throw new RuntimeException("Tên danh mục đã tồn tại");
        }

        MenuCategory saved = menuCategoryRepository.save(
                MenuCategory.builder()
                        .name(request.getName())
                        .icon(request.getIcon())
                        .sortOrder(request.getSortOrder())
                        .build()
        );

        return convertToResponse(saved);
    }

    @Override
    public MenuCategoryResponse updateMenuCategoryById(Long categoryId, UpdateMenuCategoryRequest request) {
        MenuCategory menuCategory = menuCategoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy category với id: " + categoryId));

        if (request.getName() != null && !request.getName().isEmpty()) {
            menuCategory.setName(request.getName());
        }
        if (request.getIcon() != null && !request.getIcon().isEmpty()) {
            menuCategory.setIcon(request.getIcon());
        }
        if (request.getSortOrder() != null) {
            menuCategory.setSortOrder(request.getSortOrder());
        }
        if (request.getIsActive() != null) {
            menuCategory.setIsActive(request.getIsActive());
        }

        return convertToResponse(menuCategoryRepository.save(menuCategory));
    }

    @Override
    public List<MenuCategoryResponse> getAllMenuCategory() {
        return menuCategoryRepository.findAll().stream()
                .map(this::convertToResponseWithItems)
                .toList();
    }

    @Override
    public MenuCategoryResponse getMenuCategoryById(Long categoryId) {
        MenuCategory menuCategory = menuCategoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy MenuCategory với Id: " + categoryId));
        return convertToResponseWithItems(menuCategory);
    }

    @Override
    public void deleteCategory(Long categoryId) {
        if (!menuCategoryRepository.existsById(categoryId)) {
            throw new RuntimeException("Không tìm thấy category với id: " + categoryId);
        }
        menuCategoryRepository.deleteById(categoryId);
    }

    @Override
    public MenuCategoryResponse toggleActive(Long categoryId) {
        MenuCategory menuCategory = menuCategoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy category với Id: " + categoryId));

        menuCategory.setIsActive(!menuCategory.getIsActive());
        return convertToResponse(menuCategoryRepository.save(menuCategory));
    }

    // Private method convert
    private MenuCategoryResponse convertToResponse(MenuCategory category) {
        return MenuCategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .icon(category.getIcon())
                .sortOrder(category.getSortOrder())
                .isActive(category.getIsActive())
                .createdAt(category.getCreatedAt())
                .build();
    }

    private MenuCategoryResponse convertToResponseWithItems(MenuCategory category) {
        List<MenuItemResponse> menuItems = menuItemRepository.findByMenuCategoryId(category.getId())
                .stream()
                .map(this::convertMenuItemToResponse)
                .toList();

        return MenuCategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .icon(category.getIcon())
                .sortOrder(category.getSortOrder())
                .isActive(category.getIsActive())
                .createdAt(category.getCreatedAt())
                .menuItems(menuItems)
                .build();
    }

    private MenuItemResponse convertMenuItemToResponse(MenuItem item) {
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