package com.fuzzy.calculator.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "calculation_history")
public class CalculationHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Первичный ключ, будет автоувеличиваться (1, 2, 3...)

    private Double checkValue;
    private Double radius;
    private Double cycleMax;
    private Double resultValue;
    private String statusMessage;

    private LocalDateTime createdAt;
}
