package com.example.DIMSUM.controller;

import com.example.DIMSUM.request.MenuCategoryRequest;
import com.example.DIMSUM.request.UpdateMenuCategoryRequest;
import com.example.DIMSUM.response.ApiResponse;
import com.example.DIMSUM.response.MenuCategoryResponse;
import com.example.DIMSUM.service.MenuCategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/menu-categories")
@RequiredArgsConstructor
public class MenuCategoryController {

    private final MenuCategoryService menuCategoryService;

    @PostMapping
    public ResponseEntity<ApiResponse<MenuCategoryResponse>> createMenuCategory
            (@RequestBody @Valid MenuCategoryRequest request){
        MenuCategoryResponse menuCategoryResponse = menuCategoryService.createMenuCategory(request);
        ApiResponse<MenuCategoryResponse> response = ApiResponse.<MenuCategoryResponse>builder()
                .code(1000)
                .message("Tạo danh mục thành công")
                .result(menuCategoryResponse)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<MenuCategoryResponse>>> getAllMenuCategories(){
        List<MenuCategoryResponse> categories = menuCategoryService.getAllMenuCategory();
        ApiResponse<List<MenuCategoryResponse>> response =
                ApiResponse.<List<MenuCategoryResponse>>builder()
                        .code(1000)
                        .message("Lấy danh sách thành công")
                        .result(categories)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{categoryId}")
    public  ResponseEntity<ApiResponse<MenuCategoryResponse>> getMenuCategoryById
            (@PathVariable Long categoryId){
        MenuCategoryResponse category = menuCategoryService.getMenuCategoryById(categoryId);
        ApiResponse<MenuCategoryResponse> response = ApiResponse.<MenuCategoryResponse>builder()
                .code(1000)
                .message("Lấy thành công category")
                .result(category)
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{categoryId}")
        public ResponseEntity<ApiResponse<Void>> deleteMenuCategory(@PathVariable Long categoryId) {

        menuCategoryService.deleteCategory(categoryId);

        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .code(1000)
                .message("Xóa danh mục thành công")
                .result(null)
                .build();

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{categoryId}1")
    public ResponseEntity<ApiResponse<MenuCategoryResponse>> toggleActive(
            @PathVariable Long categoryId) {

        MenuCategoryResponse category = menuCategoryService.toggleActive(categoryId);

        ApiResponse<MenuCategoryResponse> response = ApiResponse.<MenuCategoryResponse>builder()
                .code(1000)
                .message("Thay đổi trạng thái danh mục thành công")
                .result(category)
                .build();

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<ApiResponse<MenuCategoryResponse>> updateMenuCategory(
            @PathVariable Long categoryId,
            @RequestBody @Valid UpdateMenuCategoryRequest request) {

        MenuCategoryResponse category = menuCategoryService.updateMenuCategoryById(categoryId, request);

        ApiResponse<MenuCategoryResponse> response = ApiResponse.<MenuCategoryResponse>builder()
                .code(1000)
                .message("Cập nhật danh mục thành công")
                .result(category)
                .build();

        return ResponseEntity.ok(response);
    }
}
