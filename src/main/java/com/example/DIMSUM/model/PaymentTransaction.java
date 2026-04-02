package com.example.DIMSUM.model;

import com.example.DIMSUM.enums.PaymentMethod;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payment_transactions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class PaymentTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Hóa đơn liên quan
    @ManyToOne
    @JoinColumn(name = "bill_id", nullable = false)
    private Bill bill;

    // Phương thức thanh toán: CASH, MOMO, VNPAY...
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentMethod method;

    // Số tiền thanh toán trong giao dịch này
    @Column(nullable = false)
    private BigDecimal amount;

    // Thời gian thực hiện giao dịch
    private LocalDateTime paidAt;

    // Tự động set thời gian khi insert
    @PrePersist
    protected void onCreate() {
        paidAt = LocalDateTime.now();
    }
}