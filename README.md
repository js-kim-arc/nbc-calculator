# nbc-calculator
내일배움캠프 계산기 과제입니다

---

## 파일 구조

```
src/
├── App.java
└── domain/
    ├── OperatorType.java
    └── Calculator/
        ├── Calculator.java
        └── ArithmeticCalculator.java

docs/
├── domain-modeling.md
├── workflows.md
└── README.md
```

---

## 파일 설명

### `App.java`
프로그램 진입점. Scanner로 사용자 입력을 받아 연산을 위임하고 결과를 출력한다. 흐름 제어와 예외 처리를 담당한다.

### `OperatorType.java`
사칙연산 기호(`+`, `-`, `*`, `/`)를 Enum 타입으로 정의한다. 각 멤버가 연산 행위를 직접 구현하며, `from(char symbol)`으로 기호를 Enum으로 변환한다.

### `Calculator/Calculator.java`
Step 1에서 작성한 절차적 계산기 클래스. `int` 타입 피연산자를 받아 연산하고 결과를 내부 컬렉션에 저장한다.

### `Calculator/ArithmeticCalculator.java`
제네릭 기반 계산기 클래스. `<T extends Number>`를 통해 `Integer`, `Double` 등 다양한 타입을 피연산자로 받는다. 연산은 `OperatorType`에 위임하며, 스트림 기반 결과 조회 메서드를 제공한다.

### `docs/domain-modeling.md`
`Calculator`, `ArithmeticCalculator`, `OperatorType`, `App` 각 클래스의 속성, 행위, 규칙, 검증 조건을 정의한 도메인 모델 문서.

### `docs/workflows.md`
Epic과 Story 단위로 구성된 개발 흐름 문서. 절차적 계산기 구현부터 Enum, 제네릭, 람다 & 스트림 도입까지의 완료 기준을 기술한다.