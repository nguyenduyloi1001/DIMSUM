package com.example.DIMSUM.model;

import com.example.DIMSUM.enums.NotificationType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Notification {//Danh sách việc cần làm cho nhân viên/bếp theo thời gian thực
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "bill_id",nullable = false)
    private Bill bill;

    @ManyToOne
    @JoinColumn(name = "order_batch_id")
    private OrderBatch orderBatch;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationType notificationType;

    @Column(nullable = false)
    private String message;

    private Boolean isRead=false;

    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate(){
        createdAt=LocalDateTime.now();
    }
}
