package com.fuzzy.calculator.service;

import com.fuzzy.calculator.dto.FuzzyRequest;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class FuzzyLogicServiceTest {
     private final FuzzyLogicService fuzzyLogicService = new FuzzyLogicService();

    @Test
    void testCyclicalFuzzy_WithTwoPeaks() {
        // 1. ARRANGE (Подготовка данных)
        FuzzyRequest request = new FuzzyRequest();
        request.setCheckValue(3.525); // Точка входа
        request.setRadius(1.0);        // Радиус
        request.setCycleMax(6.0);      // Цикл

        // Удобный способ создать Map в одну строку (доступно с Java 9)
        request.setHistory(Map.of(
                4.0, 4,
                2.0, 2
        ));

        // 2. ACT (Выполнение действия)
        double actualResult = fuzzyLogicService.calculate(request);

        // 3. ASSERT (Проверка результата)
        double expectedResult = 52.5;

        // ВАЖНО: При сравнении дробных чисел (double) в Java всегда нужно указывать погрешность (delta).
        // Здесь 0.01 означает, что если ответ будет 52.501 или 52.499, тест пройдет.
        assertEquals(expectedResult, actualResult, 0.01, "Процент рассчитан неверно!");
    }
}