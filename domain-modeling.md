# Calculator 도메인 모델링
## 속성

- `results` : 연산 결과를 누적 저장하는 컬렉션 (`List<Integer>`, 기본값 빈 리스트)

---

## 행위

- `calculate(int num1, int num2, char operator)` : 두 정수와 연산 기호를 받아 사칙연산을 수행하고, 결과를 `results`에 저장한 뒤 반환한다
- `getResults()` : 누적된 연산 결과 목록을 반환한다(getter 역할)

---

## 규칙

- 연산 결과는 `calculate()` 호출 시 자동으로 `results`에 저장된다. 외부에서 직접 추가할 수 없음
- `results`는 외부에 직접 노출되지 않는다. 조회는 반드시 `getResults()`를 통함
- 0으로 나누는 경우 연산을 수행하지 않고 예외를 던진다. - `results`에 저장되지 않는다.
- 유효하지 않은 연산 기호가 입력된 경우 예외를 던진다. - `results`에 저장되지 않는다.
- 오류 메시지의 출력은 Calculator의 책임이 아니다. 예외를 던지고, 처리는 호출자(App)에 위임한다.

---

## 검증

- `operator`가 `+`, `-`, `*`, `/` 이외의 문자이면 `IllegalArgumentException`을 던진다.
- `operator`가 `/`이고 `num2`가 `0`이면 `ArithmeticException`을 던진다.
- 예외가 발생한 경우 `results`에 해당 연산의 결과가 추가되어서는 안 된다.

---

## Calculator Aggregate

### 책임
- 두 정수와 연산 기호를 입력받아 사칙연산 결과를 계산하고 반환한다.
- 연산 결과를 내부 컬렉션에 누적 저장해 계산 이력 상태를 유지한다.
- 0 나누기, 잘못된 연산자 등 연산 오류의 감지와 예외 발생을 내부에서 책임진다.
- 결과 컬렉션을 캡슐화해 외부가 직접 상태를 변경하지 못하도록 보호한다.



---

## App
사용자 입력, 흐름 제어, 결과 출력을 담당하는 진입점을 모델링한다.

### 설명
사용자로부터 입력을 받아 `OperatorType`으로 변환하고,
`ArithmeticCalculator`에 연산을 위임한 뒤, 결과를 출력하는 흐름 제어 역할만 담당한다.
연산 로직, 유효성 검사, 상태 저장은 App의 책임이 아니다.

### 행위

- `main(String[] args)` : 프로그램 진입점. 반복 루프 안에서 입력 → 변환 → 연산 위임 → 출력 → 종료 판단 흐름을 제어한다

### 규칙

- App은 직접 연산하지 않는다. 연산은 반드시 `ArithmeticCalculator.calculate()`에 위임한다.
- App은 직접 연산자를 검증하지 않는다. `OperatorType.from()`이 던지는 예외를 catch해 메시지를 출력한다.
- App은 직접 0 나누기를 검사하지 않는다. `ArithmeticException`을 catch해 메시지를 출력한다.
- 예외 발생 시 `continue`로 루프를 재시작한다. 프로그램을 종료하지 않는다.
- 사용자가 `exit`을 입력하면 루프를 종료하고 `Scanner`를 닫는다.

### 검증

- 연산자 변환 실패(`IllegalArgumentException`)와 연산 실패(`ArithmeticException`)는 별도의 `catch` 블록으로 분리해 처리한다.
- 삭제 인덱스 입력이 숫자가 아닌 경우(`NumberFormatException`) 별도로 처리한다.
- 삭제 인덱스가 범위를 벗어난 경우(`IndexOutOfBoundsException`) 별도로 처리한다.

---

# ArithmeticCalculator 도메인 모델링
사칙연산 수행과 연산 결과 누적 저장을 담당하는 제네릭 계산기를 모델링한다.

### 설명

연산 요청을 받아 `OperatorType`에 실제 연산을 위임하고, 결과를 내부 컬렉션에 누적해 상태를 유지하는 책임 단위다.
타입 파라미터 `T`를 통해 `Integer`, `Double` 등 다양한 숫자 타입을 피연산자로 받을 수 있다. (제네레이터 도입)
어떤 타입을 사용할지는 호출부(`App`)가 결정하며, `ArithmeticCalculator` 내부는 타입에 무관하게 동작한다.
연산 결과의 저장, 조회, 삭제는 반드시 이 클래스의 메서드를 통해서만 이루어진다.

### 속성

- `results` : 연산 결과를 누적 저장하는 컬렉션 (`List<T>`, 기본값 빈 리스트, `private`)

### 행위

- `calculate(T num1, T num2, OperatorType operator)` : 두 피연산자를 `double`로 변환해 `OperatorType`에 위임하고, 결과를 `results`에 저장한 뒤 반환한다
- `getResults()` : 누적된 연산 결과 목록을 읽기 전용으로 반환한다
- `removeResult(int index)` : 인덱스에 해당하는 연산 결과를 `results`에서 삭제한다

### 규칙

- 연산 로직은 `ArithmeticCalculator` 내부에 존재하지 않는다. 반드시 `OperatorType.apply()`에 위임한다.
- 피연산자 `T`는 `Number`의 하위 타입이어야 한다. `doubleValue()`를 통해 `double`로 변환 후 연산한다.
- 연산 결과는 `calculate()` 호출 시 자동으로 `results`에 저장된다. 외부에서 직접 추가할 수 없다.
- `results`는 외부에 직접 노출되지 않는다. 조회는 반드시 `getResults()`를 통한다.
- `getResults()`는 `Collections.unmodifiableList()`로 감싸 반환한다. 외부에서 `.add()`, `.remove()`로 직접 수정할 수 없다.(내부 데이터 validation)
- 예외가 발생한 연산의 결과는 `results`에 저장되지 않는다.
- 삭제는 반드시 `removeResult(int index)`를 통한다. 외부에서 직접 컬렉션을 조작할 수 없다.

### 검증

- `operator`가 `null`이면 `calculate()`를 수행할 수 없다.
- `removeResult()`에서 `index`가 `0` 미만이거나 `results.size()` 이상이면 `IndexOutOfBoundsException`을 던진다.
- 0 나누기, 잘못된 연산자에 대한 예외는 `OperatorType` 내부에서 발생하며, `ArithmeticCalculator`는 이를 그대로 상위로 전파한다.
- `T`의 구체 타입은 클래스 선언 시점(`new ArithmeticCalculator<Integer>()`, `new ArithmeticCalculator<Double>()`)에 결정된다. 내부에서 타입을 강제하거나 분기하지 않는다.

---

## OperatorType (enum)

사칙연산 기호와 연산 행위를 하나의 타입으로 묶어 모델링한다.

### 값

- `ADD` : `+` 덧셈 연산
- `SUBTRACT` : `-` 뺄셈 연산
- `MULTIPLY` : `*` 곱셈 연산
- `DIVIDE` : `/` 나눗셈 연산

### 속성

- `symbol` : 연산자 기호 (`char`, 각 멤버 고유값)

### 행위

- `apply(double num1, double num2)` : 각 Enum 멤버가 직접 구현하는 연산 메서드. `double` 타입 두 수를 받아 `double` 연산 결과를 반환한다
- `from(char symbol)` : 연산 기호를 받아 대응하는 `OperatorType`을 반환하는 정적 팩토리 메서드

### 규칙

- `apply()`는 `double` 기반으로 동작한다. 제네릭 피연산자 `T`는 `ArithmeticCalculator`에서 `doubleValue()`로 변환된 뒤 전달된다.
- 연산 기호(`symbol`)는 각 멤버 생성 시 고정된다. 외부에서 변경할 수 없다.
- 각 멤버는 자신의 `apply()`를 직접 구현한다. 연산 로직은 외부 클래스에 존재하지 않는다.
- `DIVIDE`의 경우 `num2`가 `0`이면 연산을 수행하지 않고 예외를 던진다.
- `from()`은 모든 멤버를 순회해 `symbol`이 일치하는 멤버를 반환한다. 일치하는 멤버가 없으면 예외를 던진다.
- 연산자의 유효성 검사는 `from()` 호출 시점에 이루어진다. `ArithmeticCalculator`는 유효성을 재검사하지 않는다.

### 검증

- `from()`에서 일치하는 `symbol`이 없으면 `IllegalArgumentException`을 던진다.
- `DIVIDE.apply()`에서 `num2 == 0`이면 `ArithmeticException`을 던진다.
- enum 값은 외부 입력값으로 직접 결정하지 않는다. 반드시 `from(char symbol)`을 통해서만 변환된다.

---

# 책임 분리

## ArithmeticCalculator\<T extends Number\> Aggregate

### 책임

- `OperatorType`에 연산을 위임하고 결과를 수신한다.
- 타입 파라미터 `T`를 통해 `Integer`, `Double` 등 다양한 숫자 타입을 피연산자로 수용한다.
- 피연산자를 `double`로 변환하는 책임을 내부에서 담당해, `OperatorType`이 타입을 알 필요가 없도록 한다.
- 연산 결과를 내부 컬렉션에 누적 저장해 계산 이력 상태를 유지한다.
- 결과 컬렉션을 캡슐화해 외부가 직접 상태를 변경하지 못하도록 보호한다.
- 인덱스 기반 결과 삭제를 단일 진입점(`removeResult`)으로 제공한다.

---

## OperatorType Aggregate

### 책임

- 사칙연산 기호를 타입으로 격상해 잘못된 기호가 시스템 내부로 유입되지 않도록 차단한다.
- `apply()`를 `double` 기반으로 통일해, 피연산자의 구체 타입(`Integer`, `Double`)을 알지 못해도 연산이 가능하도록 한다.
- 각 연산자 멤버가 자신의 연산 행위를 직접 구현해, 연산 로직이 외부 클래스에 흩어지지 않도록 응집한다.
- `from()`을 통해 외부 입력(`char`)을 안전하게 내부 타입으로 변환하는 단일 진입점을 제공한다.
- 0 나누기 예외를 연산 행위 내부에서 직접 발생시켜, 오류 감지 책임을 스스로 진다.
