package domain.Calculator;

import domain.OperatorType;

import java.util.*;
import java.util.stream.Collectors;

public class ArithmeticCalculator<T extends Number> {

    private final List<Double> results = new ArrayList<>();

    public double calculate(T num1, T num2, OperatorType operator) {
        double result = operator.apply(num1.doubleValue(), num2.doubleValue());
        results.add(result);
        return result;
    }

    public List<Double> getResults() {
        return Collections.unmodifiableList(results);
    }

    public void removeResult(int index) {
        if (index < 0 || index >= results.size()) {
            throw new IndexOutOfBoundsException("유효하지 않은 인덱스입니다: " + index);
        }
        results.remove(index);
    }

    // 기본 요구사항 — 기준값보다 큰 결과 목록
    public List<Double> getResultsGreaterThan(T basis) {
        return results.stream()
                      .filter(result -> result > basis.doubleValue())
                      .collect(Collectors.toList());
    }

    // 추가 과제 — 기준값보다 작은 결과 목록
    public List<Double> getResultsLessThan(T basis) {
        return results.stream()
                      .filter(result -> result < basis.doubleValue())
                      .collect(Collectors.toList());
    }

}