package com.example.DIMSUM.model;

import com.example.DIMSUM.enums.TableStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiningTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Số bàn (VD: Bàn 1, Bàn 2...)
    @Column(nullable = false, unique = true)
    private String tableNumber;

    // Số lượng khách tối đa
    private int capacity;

    // Trạng thái bàn: TRONG, DANG_SU_DUNG, DAT_TRUOC
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TableStatus status;

    // QR code (link hoặc mã)
    private String qrCode;
}