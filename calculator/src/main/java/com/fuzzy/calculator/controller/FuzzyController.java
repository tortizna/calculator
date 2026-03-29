package com.fuzzy.calculator.controller;

import com.fuzzy.calculator.dto.FuzzyRequest;
import com.fuzzy.calculator.dto.FuzzyResponse;
import com.fuzzy.calculator.entity.CalculationHistory;
import com.fuzzy.calculator.repository.CalculationHistoryRepository;
import com.fuzzy.calculator.service.FuzzyLogicService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/fuzzy")
@RequiredArgsConstructor
public class FuzzyController {

    private final FuzzyLogicService fuzzyLogicService;

    private final CalculationHistoryRepository historyRepository;


    @PostMapping("/calculate")
    public FuzzyResponse calculate(@RequestBody FuzzyRequest request) {
        return fuzzyLogicService.calculateAndSave(request);

    }

    @GetMapping("/history")
    public List<CalculationHistory> getHistory() {
        return fuzzyLogicService.getHistory();
    }
}
