package com.example.DIMSUM.model;

import com.example.DIMSUM.enums.OrderItemStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "order_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class OrderItem { //la 1 món cụ thể trong bill
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_batch_id",nullable = false)
    private OrderBatch orderBatch;

    @ManyToOne
    @JoinColumn(name = "menu_item_id",nullable = false)
    private MenuItem menuItem;

    @Column(nullable = false)
    private Integer quantity = 1;

    @Column(nullable = false)
    private BigDecimal unitPrice;

    private String itemNotes;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderItemStatus orderItemStatus = OrderItemStatus.PENDING;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist(){
        createdAt=LocalDateTime.now();
        updatedAt=LocalDateTime.now();
    }
    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }

}
