package domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ArithmeticCalculator {

    private final List<Integer> results = new ArrayList<>();

    public int calculate(int num1, int num2, OperatorType operator) {
        int result = (int) operator.apply(num1, num2);  // 연산을 Enum에 위임
        results.add(result);
        return result;
    }

    public List<Integer> getResults() {
        return Collections.unmodifiableList(results);
    }

    public void removeResult(int index) {
        if (index < 0 || index >= results.size()) {
            throw new IndexOutOfBoundsException("유효하지 않은 인덱스입니다: " + index);
        }
        results.remove(index);
    }
}
