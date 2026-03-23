package domain;

import java.util.ArrayList;
import java.util.List;

public class Calculator {

    private final List<Integer> results = new ArrayList<>();

    public int calculate(int num1, int num2, char operator) {
        int result = switch (operator) {
            case '+' -> num1 + num2;
            case '-' -> num1 - num2;
            case '*' -> num1 * num2;
            case '/' -> {
                if (num2 == 0) {
                    throw new ArithmeticException("나눗셈 연산에서 분모(두번째 정수)에 0이 입력될 수 없습니다.");
                }
                yield num1 / num2;
            }
            default -> throw new IllegalArgumentException("올바른 사칙연산 기호를 입력하세요. (+, -, *, /)");
        };

        results.add(result);
        return result;
    }

    public List<Integer> getResults() {
        return results;
    }
}
