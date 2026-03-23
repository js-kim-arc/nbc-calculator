
import domain.Calculator.ArithmeticCalculator;
import domain.OperatorType;

import java.util.Scanner;


public class App {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        // Calc 변환
        domain.Calculator.ArithmeticCalculator calculator = new domain.Calculator.ArithmeticCalculator();

        while (true) {
            System.out.print("첫 번째 숫자를 입력하세요: ");
            int firstNumber = sc.nextInt();

            System.out.print("두 번째 숫자를 입력하세요: ");
            int secondNumber = sc.nextInt();

            System.out.print("사칙연산 기호를 입력하세요: ");
            char symbol = sc.next().charAt(0);

            // char → OperatorType 변환
            OperatorType operator;
            try {
                operator = OperatorType.from(symbol);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                continue;
            }

            try {
                int result = calculator.calculate(firstNumber, secondNumber, operator);
                System.out.println("결과: " + result);
            } catch (ArithmeticException e) {
                System.out.println(e.getMessage());
                continue;
            }

            System.out.println("저장된 결과 목록: " + calculator.getResults());

            System.out.print("결과를 삭제하시겠습니까? (삭제할 인덱스 입력, 건너뛰려면 no): ");
            String deleteInput = sc.next();
            if (!deleteInput.equals("no")) {
                try {
                    int index = Integer.parseInt(deleteInput);
                    calculator.removeResult(index);
                    System.out.println("삭제 완료. 현재 목록: " + calculator.getResults());
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