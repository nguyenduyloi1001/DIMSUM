package com.example.DIMSUM.repository;

import com.example.DIMSUM.enums.TableStatus;
import com.example.DIMSUM.model.DiningTable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DiningTableRepository  extends JpaRepository<DiningTable,Long> {
    boolean existsByTableNumber(String tableNumber);
    boolean existsByTableNumberAndIdNot(String tableNumber, Long id);
    List<DiningTable> findByStatus(TableStatus status);
}
