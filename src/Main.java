import java.util.Scanner;


public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        while (true) {
            // Story 1 : 입력
            System.out.print("첫 번째 숫자를 입력하세요: ");
            int firstNumber = sc.nextInt();

            System.out.print("두 번째 숫자를 입력하세요: ");
            int secondNumber = sc.nextInt();

            System.out.print("사칙연산 기호를 입력하세요: ");
            char operator = sc.next().charAt(0);

            // Story 2 : 연산
            int result = 0;

            switch (operator) {
                case '+':
                    result = firstNumber + secondNumber;
                    break;
                case '-':
                    result = firstNumber - secondNumber;
                    break;
                case '*':
                    result = firstNumber * secondNumber;
                    break;
                case '/':
                    if (secondNumber == 0) {
                        System.out.println("나눗셈 연산에서 분모(두번째 정수)에 0이 입력될 수 없습니다.");
                        continue;
                    }
                    result = firstNumber / secondNumber;
                    break;
                default:
                    System.out.println("올바른 사칙연산 기호를 입력하세요. (+, -, *, /)");
                    continue;
            }

            System.out.println("결과: " + result);

            // Story 3 : 반복 여부 확인
            System.out.print("더 계산하시겠습니까? (exit 입력 시 종료): ");
            String input = sc.next();

            if (input.equals("exit")) {
                break;
            }
        }

        sc.close();
    }
}