package com.fuzzy.calculator.dto;

import lombok.Data;
import java.util.Map;

@Data
public class FuzzyRequest {
     private Double checkValue;
     private Double radius;
     private Double cycleMax;
     private Map<Double,Integer> history;
}
