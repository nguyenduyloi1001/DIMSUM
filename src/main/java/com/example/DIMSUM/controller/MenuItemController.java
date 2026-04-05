package com.example.DIMSUM.controller;

import com.example.DIMSUM.request.MenuItemRequest;
import com.example.DIMSUM.request.UpdateMenuItemRequest;
import com.example.DIMSUM.response.ApiResponse;
import com.example.DIMSUM.response.MenuItemResponse;
import com.example.DIMSUM.service.MenuItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/menu-item")
@RequiredArgsConstructor
public class MenuItemController {

    private final MenuItemService menuItemService;

    @PostMapping
    public ResponseEntity<ApiResponse<MenuItemResponse>> createMenuItem(@RequestBody MenuItemRequest request) {
        MenuItemResponse menuItemResponse = menuItemService.createMenuItem(request);

        ApiResponse<MenuItemResponse> response = ApiResponse.<MenuItemResponse>builder()
                .code(1000)
                .message("Tạo thành công sản phẩm")
                .result(menuItemResponse)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<MenuItemResponse>>> getAllMenuItems() {
        List<MenuItemResponse> menuItemResponse = menuItemService.getAllMenuItems();
        ApiResponse<List<MenuItemResponse>> response = ApiResponse.<List<MenuItemResponse>>builder()
                .code(1000)
                .message("Lấy danh sách menu thành công")
                .result(menuItemResponse)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{menuItemId}")
    public ResponseEntity<ApiResponse<MenuItemResponse>> getMenuItemById(@PathVariable Long menuItemId) {
        MenuItemResponse menuItemResponse = menuItemService.getMenuItemById(menuItemId);
        ApiResponse<MenuItemResponse> response = ApiResponse.<MenuItemResponse>builder()
                .code(1000)
                .message("Lấy sản phẩm thành công")
                .result(menuItemResponse)
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{menuItemId}")
    public ResponseEntity<ApiResponse<MenuItemResponse>> updateMenuItemById(
            @PathVariable Long menuItemId,
            @RequestBody UpdateMenuItemRequest request) {
        MenuItemResponse menuItemResponse = menuItemService.updateMenuItemById(menuItemId, request);
        ApiResponse<MenuItemResponse> response = ApiResponse.<MenuItemResponse>builder()
                .code(1000)
                .message("Cập nhật sản phẩm thành công")
                .result(menuItemResponse)
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{menuItemId}")
    public ResponseEntity<ApiResponse<Void>> deleteMenuItemById(@PathVariable Long menuItemId) {
        menuItemService.deleteMenuItemById(menuItemId);
        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .code(1000)
                .message("Xóa sản phẩm thành công")
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<ApiResponse<List<MenuItemResponse>>> getMenuItemByCategory(@PathVariable Long categoryId) {
        List<MenuItemResponse> menuItemResponses = menuItemService.getMenuItemByCategory(categoryId);
        ApiResponse<List<MenuItemResponse>> response = ApiResponse.<List<MenuItemResponse>>builder()
                .code(1000)
                .message("Lấy danh sách món theo danh mục thành công")
                .result(menuItemResponses)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/available")
    public ResponseEntity<ApiResponse<List<MenuItemResponse>>> getAvailableMenuItems() {
        List<MenuItemResponse> menuItemResponses = menuItemService.getAvailableMenuItems();
        ApiResponse<List<MenuItemResponse>> response = ApiResponse.<List<MenuItemResponse>>builder()
                .code(1000)
                .message("Lấy danh sách món còn bán thành công")
                .result(menuItemResponses)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/featured")
    public ResponseEntity<ApiResponse<List<MenuItemResponse>>> getFeaturedMenuItems() {
        List<MenuItemResponse> menuItemResponses = menuItemService.getFeaturedMenuItems();
        ApiResponse<List<MenuItemResponse>> response = ApiResponse.<List<MenuItemResponse>>builder()
                .code(1000)
                .message("Lấy danh sách món nổi bật thành công")
                .result(menuItemResponses)
                .build();
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{menuItemId}/toggle-availability")
    public ResponseEntity<ApiResponse<MenuItemResponse>> toggleAvailability(@PathVariable Long menuItemId) {
        MenuItemResponse menuItemResponse = menuItemService.toggleAvailability(menuItemId);
        ApiResponse<MenuItemResponse> response = ApiResponse.<MenuItemResponse>builder()
                .code(1000)
                .message("Chuyển trạng thái còn bán thành công")
                .result(menuItemResponse)
                .build();
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{menuItemId}/toggle-featured")
    public ResponseEntity<ApiResponse<MenuItemResponse>> toggleFeatured(@PathVariable Long menuItemId) {
        MenuItemResponse menuItemResponse = menuItemService.toggleFeatured(menuItemId);
        ApiResponse<MenuItemResponse> response = ApiResponse.<MenuItemResponse>builder()
                .code(1000)
                .message("Chuyển trạng thái nổi bật thành công")
                .result(menuItemResponse)
                .build();
        return ResponseEntity.ok(response);
    }
}