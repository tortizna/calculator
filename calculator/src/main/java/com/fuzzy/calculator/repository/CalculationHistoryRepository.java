package com.fuzzy.calculator.repository;

import com.fuzzy.calculator.entity.CalculationHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CalculationHistoryRepository extends JpaRepository<CalculationHistory, Long> {
}
