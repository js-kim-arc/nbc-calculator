
import domain.Calculator.ArithmeticCalculator;
import domain.OperatorType;

import java.util.Scanner;


public class App {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        ArithmeticCalculator<Integer> intCalculator = new ArithmeticCalculator<>();
        ArithmeticCalculator<Double> doubleCalculator = new ArithmeticCalculator<>();

        while (true) {
            System.out.print("첫 번째 숫자를 입력하세요: ");
            String firstInput = sc.next();

            System.out.print("두 번째 숫자를 입력하세요: ");
            String secondInput = sc.next();

            System.out.print("사칙연산 기호를 입력하세요: ");
            char symbol = sc.next().charAt(0);

            OperatorType operator;
            try {
                operator = OperatorType.from(symbol);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                continue;
            }

            // 소수점 포함 여부로 계산기 분기
            boolean isDouble = firstInput.contains(".") || secondInput.contains(".");

            try {
                if (isDouble) {
                    double num1 = Double.parseDouble(firstInput);
                    double num2 = Double.parseDouble(secondInput);
                    double result = doubleCalculator.calculate(num1, num2, operator);
                    System.out.println("결과 (Double 계산기): " + result);
                    System.out.println("저장된 결과 목록: " + doubleCalculator.getResults());
                } else {
                    int num1 = Integer.parseInt(firstInput);
                    int num2 = Integer.parseInt(secondInput);
                    double result = intCalculator.calculate(num1, num2, operator);
                    System.out.println("결과 (Integer 계산기): " + result);
                    System.out.println("저장된 결과 목록: " + intCalculator.getResults());
                }
            } catch (NumberFormatException e) {
                System.out.println("올바른 숫자를 입력하세요.");
                continue;
            } catch (ArithmeticException e) {
                System.out.println(e.getMessage());
                continue;
            }

            System.out.print("결과를 삭제하시겠습니까? (삭제할 인덱스 입력, 건너뛰려면 no): ");
            String deleteInput = sc.next();
            if (!deleteInput.equals("no")) {
                try {
                    int index = Integer.parseInt(deleteInput);
                    if (isDouble) {
                        doubleCalculator.removeResult(index);
                        System.out.println("삭제 완료. 현재 목록: " + doubleCalculator.getResults());
                    } else {
                        intCalculator.removeResult(index);
                        System.out.println("삭제 완료. 현재 목록: " + intCalculator.getResults());
                    }
                } catch (NumberFormatException e) {
                    System.out.println("유효한 숫자 또는 'no'를 입력하세요.");
                } catch (IndexOutOfBoundsException e) {
                    System.out.println(e.getMessage());
                }
            }

            System.out.print("더 계산하시겠습니까? (exit 입력 시 종료): ");
            if (sc.next().equals("exit")) break;
        }

        sc.close();
    }
}