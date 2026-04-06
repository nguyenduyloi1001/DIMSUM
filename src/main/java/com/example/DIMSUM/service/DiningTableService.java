package com.example.DIMSUM.service;

import com.example.DIMSUM.enums.TableStatus;
import com.example.DIMSUM.request.DiningTableRequest;
import com.example.DIMSUM.request.UpdateDiningTableRequest;
import com.example.DIMSUM.response.DiningTableResponse;

import java.util.List;

public interface DiningTableService {
    DiningTableResponse createTable(DiningTableRequest request);

    DiningTableResponse updateTable(Long id, UpdateDiningTableRequest request);

    DiningTableResponse getTableById(Long id);

    List<DiningTableResponse> getAllTables();

    void deleteTable(Long id);

    List<DiningTableResponse> getTablesByStatus(TableStatus status);

    List<DiningTableResponse> getAvailableTables();

    List<DiningTableResponse> getOccupiedTables();

    boolean isTableAvailable(Long id);

    // TRỐNG -> ĐANG CÓ KHÁCH (khách quét QR vào)
    DiningTableResponse occupyTable(Long id);

    // ĐANG CÓ KHÁCH -> TRỐNG (khách thanh toán xong)
    DiningTableResponse freeTable(Long id);

    String getQRContent(Long tableId);

    String getQRImagePath(Long tableId);

}
