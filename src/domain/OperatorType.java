package domain;

public enum OperatorType {
    ADD('+') {
        @Override
        public double apply(double num1, double num2) { return num1 + num2; }
    },
    SUBTRACT('-') {
        @Override
        public double apply(double num1, double num2) { return num1 - num2; }
    },
    MULTIPLY('*') {
        @Override
        public double apply(double num1, double num2) { return num1 * num2; }
    },
    DIVIDE('/') {
        @Override
        public double apply(double num1, double num2) {
            if (num2 == 0) {
                throw new ArithmeticException("나눗셈 연산에서 분모(두번째 정수)에 0이 입력될 수 없습니다.");
            }
            return num1 / num2;
        }
    };

    private final char symbol;

    OperatorType(char symbol) {
        this.symbol = symbol;
    }

    public abstract double apply(double num1, double num2);

    public static OperatorType from(char symbol) {
        for (OperatorType type : values()) {
            if (type.symbol == symbol) {
                return type;
            }
        }
        throw new IllegalArgumentException("올바른 사칙연산 기호를 입력하세요. (+, -, *, /)");
    }
}