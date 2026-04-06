    package com.example.DIMSUM.service.Impl;

    import com.example.DIMSUM.enums.TableStatus;
    import com.example.DIMSUM.model.DiningTable;
    import com.example.DIMSUM.repository.DiningTableRepository;
    import com.example.DIMSUM.request.DiningTableRequest;
    import com.example.DIMSUM.request.UpdateDiningTableRequest;
    import com.example.DIMSUM.response.DiningTableResponse;
    import com.example.DIMSUM.service.DiningTableService;
    import com.google.zxing.BarcodeFormat;
    import com.google.zxing.EncodeHintType;
    import com.google.zxing.WriterException;
    import com.google.zxing.client.j2se.MatrixToImageWriter;
    import com.google.zxing.common.BitMatrix;
    import com.google.zxing.qrcode.QRCodeWriter;
    import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
    import lombok.RequiredArgsConstructor;
    import org.springframework.beans.factory.annotation.Value;
    import lombok.extern.slf4j.Slf4j;
    import org.springframework.stereotype.Service;

    import java.io.IOException;
    import java.nio.file.Files;
    import java.nio.file.Path;
    import java.nio.file.Paths;
    import java.util.List;
    import java.util.Map;

    @Service
    @Slf4j
    @RequiredArgsConstructor
    public class DiningTableServiceImpl implements DiningTableService {
        private final DiningTableRepository diningTableRepository;

        @Value("${app.qr.storage-path:uploads/qrcodes/}")
        private String storagePath;

        @Value("${app.qr.base-url:https://dimsum.example.com/menu}")
        private String baseUrl;

        @Value("${app.qr.width:300}")
        private int width;

        @Value("${app.qr.height:300}")
        private int height;
        @Override
        public DiningTableResponse createTable(DiningTableRequest request) {
            if(diningTableRepository.existsByTableNumber(request.getTableNumber())){
                throw new RuntimeException("Bàn số" + request.getTableNumber() + " đã tồn tại");
            }
            String qrContent = buildQRContent(request.getTableNumber());
            String qrImagePath= generateAndSaveQRImage(qrContent, request.getTableNumber());
            DiningTable table= DiningTable.builder()
                    .tableNumber(request.getTableNumber())
                    .capacity(request.getCapacity())
                    .status(TableStatus.AVAILABLE)
                    .qrCode(qrContent)
                    .qrImagePath(qrImagePath)
                    .build();
            DiningTable saved = diningTableRepository.save(table);
            return mapToResponse(saved);
        }

        @Override
        public DiningTableResponse updateTable(Long id, UpdateDiningTableRequest request) {
            DiningTable existing = diningTableRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException(
                            "Không tìm thấy bàn với ID: " + id));

            if (request.getTableNumber() != null
                    && !request.getTableNumber().equals(existing.getTableNumber())
                    && diningTableRepository.existsByTableNumber(request.getTableNumber())) {
                throw new RuntimeException(
                        "Bàn số '" + request.getTableNumber() + "' đã tồn tại");
            }

            // Nếu đổi số bàn → xóa QR cũ, generate QR mới
            if (request.getTableNumber() != null
                    && !request.getTableNumber().equals(existing.getTableNumber())) {
                deleteQRImage(existing.getQrImagePath());
                String qrContent   = buildQRContent(request.getTableNumber());
                String qrImagePath = generateAndSaveQRImage(qrContent, request.getTableNumber());
                existing.setTableNumber(request.getTableNumber());
                existing.setQrCode(qrContent);
                existing.setQrImagePath(qrImagePath);
            }

            if (request.getCapacity() != null) existing.setCapacity(request.getCapacity());
            if (request.getStatus()   != null) existing.setStatus(request.getStatus());

            DiningTable updated = diningTableRepository.save(existing);
            return mapToResponse(updated);
        }

        @Override
        public DiningTableResponse getTableById(Long id) {
            return mapToResponse(findTableOrThrow(id));
        }

        @Override
        public List<DiningTableResponse> getAllTables() {
            return diningTableRepository.findAll()
                    .stream()
                    .map(this::mapToResponse).toList();
        }

        @Override
        public void deleteTable(Long id) {
            DiningTable table = findTableOrThrow(id);
            deleteQRImage(table.getQrImagePath());
            diningTableRepository.delete(table);
            log.info("Deleted table id={}, number={}", id, table.getTableNumber());

        }

        @Override
        public List<DiningTableResponse> getTablesByStatus(TableStatus status) {
            return diningTableRepository.findByStatus(status).stream().map(this::mapToResponse).toList();
        }

        @Override
        public List<DiningTableResponse> getAvailableTables() {
            return getTablesByStatus(TableStatus.AVAILABLE);
        }

        @Override
        public List<DiningTableResponse> getOccupiedTables() {
            return getTablesByStatus(TableStatus.OCCUPIED);
        }

        @Override
        public boolean isTableAvailable(Long id) {
            return findTableOrThrow(id).getStatus() == TableStatus.AVAILABLE;
        }

        @Override
        public DiningTableResponse occupyTable(Long id) {
            DiningTable table = findTableOrThrow(id);
            if(table.getStatus()!= TableStatus.AVAILABLE)
                throw new RuntimeException(
                        "Bàn không khả dụng - trạng thái hiện tại: " + table.getStatus());
            table.setStatus(TableStatus.OCCUPIED);
            return mapToResponse(diningTableRepository.save(table));
        }

        @Override
        public DiningTableResponse freeTable(Long id) {
            DiningTable table = findTableOrThrow(id);
            if(table.getStatus() !=TableStatus.OCCUPIED){
                throw new RuntimeException(
                        "Bàn chưa được sử dụng - trạng thái hiện tại: " + table.getStatus());
            }
            table.setStatus(TableStatus.AVAILABLE);
            return mapToResponse(diningTableRepository.save(table));
        }

        @Override
        public String getQRContent(Long tableId) {
            return findTableOrThrow(tableId).getQrCode();
        }

        @Override
        public String getQRImagePath(Long tableId) {
            return findTableOrThrow(tableId).getQrImagePath();
        }

        // ─── QR HELPERS ──────────────────────────────────────────────────────────────

        private String buildQRContent(String tableNumber) {
            return baseUrl + "?table=" + tableNumber;
        }

        private String generateAndSaveQRImage(String content, String tableNumber) {
            try {
                Path dir = Paths.get(storagePath);
                Files.createDirectories(dir);

                String fileName = "table-" + tableNumber.replace(" ", "_") + ".png";
                Path filePath   = dir.resolve(fileName);

                Map<EncodeHintType, Object> hints = Map.of(
                        EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H,
                        EncodeHintType.MARGIN, 1
                );

                BitMatrix bitMatrix = new QRCodeWriter()
                        .encode(content, BarcodeFormat.QR_CODE, width, height, hints);
                MatrixToImageWriter.writeToPath(bitMatrix, "PNG", filePath);

                log.info("QR generated: {}", filePath);
                return filePath.toString();

            } catch (WriterException | IOException e) {
                throw new RuntimeException("Không thể tạo QR code cho bàn: " + tableNumber, e);
            }
        }

        private void deleteQRImage(String imagePath) {
            if (imagePath == null || imagePath.isBlank()) return;
            try {
                Files.deleteIfExists(Paths.get(imagePath));
            } catch (IOException e) {
                log.warn("Không thể xóa QR cũ: {}", imagePath);
            }
        }

        private DiningTableResponse mapToResponse(DiningTable diningTable){
            return DiningTableResponse.builder()
                    .id(diningTable.getId())
                    .tableNumber(diningTable.getTableNumber())
                    .capacity(diningTable.getCapacity())
                    .status(diningTable.getStatus())
                    .qrCode(diningTable.getQrCode())
                    .qrImagePath(diningTable.getQrImagePath())
                    .build();
        }

        private DiningTable findTableOrThrow(Long id){
            return diningTableRepository.findById(id)
                    .orElseThrow(()-> new RuntimeException("Không tìm thấy bàn với ID" + id));
        }
    }
