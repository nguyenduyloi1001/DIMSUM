package com.example.DIMSUM.response;

import com.example.DIMSUM.enums.TableStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class DiningTableResponse {
    private Long id;
    private String tableNumber;
    private int capacity;
    private TableStatus status;
    private String qrCode;
    private String qrImagePath;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
