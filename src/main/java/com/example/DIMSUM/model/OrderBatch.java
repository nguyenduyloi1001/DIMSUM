package com.example.DIMSUM.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order_batches")
@Builder

public class OrderBatch { //1 dot khach bam gui mon trong cung` 1 bill
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "bill_id",nullable = false)
    private Bill bill;

    @ManyToOne
    @JoinColumn(name = "staff_id")
    private User staff;

    @Column(nullable = false)
    private Integer batchNumber;

    private String note;

    private LocalDateTime createdAt;

    //1 batch co nhieu mon
    @OneToMany(mappedBy = "orderBatch",cascade = CascadeType.ALL)
    private List<OrderItem> orderItems;

    @PrePersist // ham tu chay
    protected void onCreate(){
        createdAt=LocalDateTime.now();
    }
}
