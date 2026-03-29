package com.fuzzy.calculator.service;

import com.fuzzy.calculator.dto.FuzzyRequest;
import com.fuzzy.calculator.dto.FuzzyResponse;
import com.fuzzy.calculator.entity.CalculationHistory;
import com.fuzzy.calculator.repository.CalculationHistoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class FuzzyLogicService {

    private final CalculationHistoryRepository historyRepository;

    @Transactional
    public FuzzyResponse calculateAndSave(FuzzyRequest request) {

        // 1.
        double result = calculate(request);

        String message = result > 10 ? "OK" : "ANOMALY";

        // 2. Создаем сущность для БД
        CalculationHistory historyRecord = new CalculationHistory();
        historyRecord.setCheckValue(request.getCheckValue());
        historyRecord.setRadius(request.getRadius());
        historyRecord.setCycleMax(request.getCycleMax());
        historyRecord.setResultValue(result);
        historyRecord.setStatusMessage(message);
        historyRecord.setCreatedAt(LocalDateTime.now());

        // 3. Сохраняем в базу
        historyRepository.save(historyRecord);

        // 4. Возвращаем результат контроллеру
        return new FuzzyResponse(result, message);

    }

    public double calculate(FuzzyRequest request) {

        double numerator = 0;
        double denominator = 0;
        // 2. Определить, какой алгоритм использовать (если cycleMax != null, то циклический)

        // Подсказка: тебе нужно найти Числитель (Current Height) и Знаменатель (Max Height)

        Double checkValue = request.getCheckValue();
        Double radius = request.getRadius();
        Double cycleMax = request.getCycleMax();
        Map<Double,Integer> history = request.getHistory();

        Double maxKey = Collections.max(history.entrySet(), Map.Entry.comparingByValue()).getKey();

        for(Map.Entry<Double,Integer> entry : history.entrySet()) {
            Double value = entry.getKey();
            Integer count = entry.getValue();

            numerator += calculateContribution(checkValue,value, count, radius, cycleMax);
            denominator += calculateContribution(maxKey, value, count, radius, cycleMax);
        }

        if(cycleMax != null) {
            for(Map.Entry<Double,Integer> entry : history.entrySet()) {
                Double value = entry.getKey();
                Integer count = entry.getValue();

            }
        } else {

        }

        return numerator/denominator * 100;
    }

    // Метод для расчета вклада одной точки истории в проверяемую точку
    // contribution = count * (1 - distance/radius)
    private double calculateContribution(double checkValue, double historyValue, int count, double radius, Double cycleMax) {

        double contribution = 0.0;
        double distance = getDistance(checkValue, historyValue, cycleMax);

        // Если дистанция < радиуса, то считаем, если нет, то венем 0.0
        if(distance < radius) {
            contribution = count * (1- distance/radius);
        }

        return contribution;
    }

    // Метод для поиска дистанции
    private double getDistance(double a, double b, Double cycleMax) {
        double distance = 0.0;
        double abs = Math.abs(a-b);

        if(cycleMax != null) {
            distance = abs; // просто Math.abs(a-b)
        } else {
            distance = Math.min(abs, cycleMax - abs); // формула min(|a-b|, max - |a-b|)
        }

        return distance;
    }

    public List<CalculationHistory> getHistory() {
        // Достаем все записи, сортируя по убыванию ID (новые будут первыми)
        return historyRepository.findAll(org.springframework.data.domain.Sort.by(org.springframework.data.domain.Sort.Direction.DESC, "id"));
    }
}
