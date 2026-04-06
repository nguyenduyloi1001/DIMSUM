package com.example.DIMSUM.controller;

import com.example.DIMSUM.enums.TableStatus;
import com.example.DIMSUM.request.DiningTableRequest;
import com.example.DIMSUM.request.UpdateDiningTableRequest;
import com.example.DIMSUM.response.ApiResponse;
import com.example.DIMSUM.response.DiningTableResponse;
import com.example.DIMSUM.service.DiningTableService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tables")
@RequiredArgsConstructor
public class DiningTableController {

    private final DiningTableService diningTableService;



    @PostMapping
    public ResponseEntity<ApiResponse<DiningTableResponse>> createTable(
            @Valid @RequestBody DiningTableRequest request) {
        DiningTableResponse diningTableResponse = diningTableService.createTable(request);
        ApiResponse<DiningTableResponse> response = ApiResponse.<DiningTableResponse>builder()
                .code(1000)
                .message("Tạo bàn thành công")
                .result(diningTableResponse)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<DiningTableResponse>> updateTable(
            @PathVariable Long id,
            @Valid @RequestBody UpdateDiningTableRequest request) {
        DiningTableResponse diningTableResponse = diningTableService.updateTable(id, request);
        ApiResponse<DiningTableResponse> response = ApiResponse.<DiningTableResponse>builder()
                .code(1000)
                .message("Cập nhật bàn thành công")
                .result(diningTableResponse)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<DiningTableResponse>> getTableById(@PathVariable Long id) {
        DiningTableResponse diningTableResponse = diningTableService.getTableById(id);
        ApiResponse<DiningTableResponse> response = ApiResponse.<DiningTableResponse>builder()
                .code(1000)
                .message("Lấy thông tin bàn thành công")
                .result(diningTableResponse)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<DiningTableResponse>>> getAllTables() {
        List<DiningTableResponse> diningTableResponses = diningTableService.getAllTables();
        ApiResponse<List<DiningTableResponse>> response = ApiResponse.<List<DiningTableResponse>>builder()
                .code(1000)
                .message("Lấy danh sách bàn thành công")
                .result(diningTableResponses)
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteTable(@PathVariable Long id) {
        diningTableService.deleteTable(id);
        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .code(1000)
                .message("Xóa bàn thành công")
                .build();
        return ResponseEntity.ok(response);
    }

    // ─── STATUS QUERIES ──────────────────────────────────────────────────────────

    @GetMapping("/status/{status}")
    public ResponseEntity<ApiResponse<List<DiningTableResponse>>> getTablesByStatus(
            @PathVariable TableStatus status) {
        List<DiningTableResponse> diningTableResponses = diningTableService.getTablesByStatus(status);
        ApiResponse<List<DiningTableResponse>> response = ApiResponse.<List<DiningTableResponse>>builder()
                .code(1000)
                .message("Lấy danh sách bàn theo trạng thái thành công")
                .result(diningTableResponses)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/available")
    public ResponseEntity<ApiResponse<List<DiningTableResponse>>> getAvailableTables() {
        List<DiningTableResponse> diningTableResponses = diningTableService.getAvailableTables();
        ApiResponse<List<DiningTableResponse>> response = ApiResponse.<List<DiningTableResponse>>builder()
                .code(1000)
                .message("Lấy danh sách bàn trống thành công")
                .result(diningTableResponses)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/occupied")
    public ResponseEntity<ApiResponse<List<DiningTableResponse>>> getOccupiedTables() {
        List<DiningTableResponse> diningTableResponses = diningTableService.getOccupiedTables();
        ApiResponse<List<DiningTableResponse>> response = ApiResponse.<List<DiningTableResponse>>builder()
                .code(1000)
                .message("Lấy danh sách bàn đang có khách thành công")
                .result(diningTableResponses)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/available")
    public ResponseEntity<ApiResponse<Boolean>> isTableAvailable(@PathVariable Long id) {
        boolean available = diningTableService.isTableAvailable(id);
        ApiResponse<Boolean> response = ApiResponse.<Boolean>builder()
                .code(1000)
                .message(available ? "Bàn đang trống" : "Bàn đang có khách")
                .result(available)
                .build();
        return ResponseEntity.ok(response);
    }

    // ─── STATUS TRANSITIONS ──────────────────────────────────────────────────────

    @PatchMapping("/{id}/occupy")
    public ResponseEntity<ApiResponse<DiningTableResponse>> occupyTable(@PathVariable Long id) {
        DiningTableResponse diningTableResponse = diningTableService.occupyTable(id);
        ApiResponse<DiningTableResponse> response = ApiResponse.<DiningTableResponse>builder()
                .code(1000)
                .message("Bàn đã chuyển sang trạng thái có khách")
                .result(diningTableResponse)
                .build();
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/free")
    public ResponseEntity<ApiResponse<DiningTableResponse>> freeTable(@PathVariable Long id) {
        DiningTableResponse diningTableResponse = diningTableService.freeTable(id);
        ApiResponse<DiningTableResponse> response = ApiResponse.<DiningTableResponse>builder()
                .code(1000)
                .message("Bàn đã chuyển sang trạng thái trống")
                .result(diningTableResponse)
                .build();
        return ResponseEntity.ok(response);
    }

    // ─── QR CODE ─────────────────────────────────────────────────────────────────

    @GetMapping("/{id}/qr-content")
    public ResponseEntity<ApiResponse<String>> getQRContent(@PathVariable Long id) {
        String content = diningTableService.getQRContent(id);
        ApiResponse<String> response = ApiResponse.<String>builder()
                .code(1000)
                .message("Lấy nội dung QR thành công")
                .result(content)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/qr-image-path")
    public ResponseEntity<ApiResponse<String>> getQRImagePath(@PathVariable Long id) {
        String path = diningTableService.getQRImagePath(id);
        ApiResponse<String> response = ApiResponse.<String>builder()
                .code(1000)
                .message("Lấy đường dẫn ảnh QR thành công")
                .result(path)
                .build();
        return ResponseEntity.ok(response);
    }
}