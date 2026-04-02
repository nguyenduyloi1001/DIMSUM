package com.example.DIMSUM.model;

import com.example.DIMSUM.enums.BillStatus;
import com.example.DIMSUM.enums.PaymentMethod;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "bills")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "table_id",nullable = false)
    private DiningTable table;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BillStatus billStatus;

    private BigDecimal totalAmount = BigDecimal.ZERO;



    private String note;

    private LocalDateTime openedAt;

    private LocalDateTime closedAt;

    @OneToMany(mappedBy = "bill", cascade = CascadeType.ALL)
        private List<OrderBatch> orderBatches;
    @PrePersist
    protected void onCreate() {
        openedAt = LocalDateTime.now();
    }
}
