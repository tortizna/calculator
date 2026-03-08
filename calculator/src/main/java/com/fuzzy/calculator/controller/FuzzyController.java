package com.fuzzy.calculator.controller;

import com.fuzzy.calculator.dto.FuzzyRequest;
import com.fuzzy.calculator.dto.FuzzyResponse;
import com.fuzzy.calculator.service.FuzzyLogicService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/fuzzy")
@RequiredArgsConstructor
public class FuzzyController {

    private final FuzzyLogicService fuzzyLogicService;

    @PostMapping("/calculate")
    public FuzzyResponse calculate(@RequestBody FuzzyRequest request) {
        // Тут вызываем логику
        double result = fuzzyLogicService.calculate(request);

        // Формируем ответ
        return new FuzzyResponse(result, result > 10 ? "OK" : "ANOMALY");
    }
}
