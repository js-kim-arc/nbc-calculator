package domain.Calculator;

import domain.OperatorType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
}