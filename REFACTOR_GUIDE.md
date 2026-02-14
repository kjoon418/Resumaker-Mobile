# Resumaker 리팩터링 가이드

### Composable을 Stateless와 Stateful로 분리한다.
상세 방식:
- Stateful Composable은 오직 ViewModel과만 통신한다.
- Stateless Composable은 오직 매개변수로 전달받은 데이터와 콜백 람다만 처리한다.

효과:
- UI 프리뷰 작성이 쉬워진다.
- 테스트 코드 작성 시 의존성을 주입할 필요 없어진다.

### 도메인 중심 디자인 시스템을 구축한다.
상세 방식:
- MaterialTheme을 넘어 앱 고유의 DesignSystem 래퍼를 구축한다.
- AppColors, AppTypography, AppSpacing 등을 별도의 LocalComposition으로 관리한다.

효과:
- 라이브러리가 업데이트되더라도 비즈니스 디자인 언어는 보호받는다.
- 최소한의 코드 변경으로 앱 전체 UI를 수정할 수 있다.

### Side-Effect를 격리한다.
상세 방식:
- LaunchedEffect, SideEffect, DisposableEffect 등을 무분별하게 사용하지 않는다.
- Effect Coordinator 패턴을 도입하여, UI 레이어에서 발생하는 부수 효과를 하나의 Channel로 모아 ViewModel에서 처리한다.
- UI 이벤트는 ViewModel의 Intent로 전달하고, 그 결과로 발생하는 부수 효과는 `LaunchedEffect` 내에서 `collect`하여 처리한다.

효과:
- Race Condition을 방지한다.
- Crash 확률이 줄어든다.

### Slot API 기반으로 컴포넌트를 조립한다.
상세 방식:
- 매개변수로 단순 데이터 대신 `content: @Composable () -> Unit`과 같은 Slot을 활용한다.

효과:
- 코드 중복을 제거할 수 있다.
- 공통 레이아웃만 선언하고 알맹이를 갈아끼는 방식이므로, 유연한 UI 확장이 가능하다.

### 안정성 지표를 최적화 및 강제화한다.
상세 방식:
- `@Stable`과 `@Immutable` 어노테이션을 도메인 모델에 명시적으로 부여한다.
- Compose 컴파일러 리포트를 주기적으로 확인해, 불필요한 Recomposition이 발생하는 클래스를 찾아내 리팩터링한다.

효과:
- 저사양 기기에서도 부드러운 애니메이션을 제공할 수 있다.

### MVI(Model-View-Intent) 아키텍처로 전환한다.
상세 방식:
- ViewModel에 변수를 단순히 나열하지 않고, 하나의 `State` 클래스와 `Intent(Action)` 흐름으로 리팩터링한다.
- `viewState.copy()`를 통한 불변성 유지에 집중한다.
- Side Effect 처리를 위해, `Channel`이나 `SharedFlow`를 활용한 `Effect` 스트림을 분리한다.

효과:
- 앱의 현재 상태를 스냅샷으로 저장하기 쉽다.
- 디버깅 시 타임라인을 추적할 수 있다.

### Modifier 확장 함수를 통해 가독성을 확보한다.
상세 방식:
- Modifier의 체이닝이 길어질 경우, 의미 있는 단위로 확장 함수를 선언한다. (예시: `standardPadding()`)

효과:
- 코드가 '어떻게(How)'가 아닌 '무엇(What)'을 하는지 설명하게 되어 가독성이 향상된다.

### 프리뷰 데이터 프로바이더를 구현한다.
상세 방식:
- 더미 데이터를 하드코딩 하지 않고 `PreviewParameterProvider`를 구현한다.
- 다양한 상태(Empty, Loading, Error, Success)에 대한 UI를 한 눈에 확인할 수 있는 통합 테스트 뷰를 구축한다.

효과:
- 수정 사항이 발생했을 때 에뮬레이터를 돌리지 않고도 즉각적으로 확인할 수 있다.

### Navigation Wrapper 및 Type-safe Routes를 사용한다.
상세 방식:
- 문자열 기반의 Navigation Route를 지양한다.
- Kotlin Serialization을 활용한 Type-safe Navigation으로 리팩터링하고, 각 화면의 인자(Argument)를 클래스 형태로 정의한다.

효과:
- 런타임에 발생하는 경로 오류와 타입 불일치 오류를 컴파일 시점에 차단한다.

### UseCase 레이어를 통해 ViewModel을 경량화한다.
상세 방식:
- ViewModel이 Repository를 직접 호출하지 않게 한다.
- 단일 책임 원칙(SRP)에 따라, 하나의 비즈니스 로직만 수행하는 `UseCase` 클래스를 도입한다.

효과:
- 여러 ViewModel에서 동일한 로직을 재사용할 수 있다.
- 단위 테스트의 범위가 명확해진다.

### 코루틴 스코프를 정밀 제어한다.
상세 방식:
- `viewModelScope` 외에도 UI 로직을 위한 `rememberCoroutineScope`의 생명주기를 엄격히 관리한다.
- 긴 작업은 도메인 레이어의 스코프로 넘기고, UI는 오직 상태 변경만 관찰하게 한다.

효과:
- 메모리 누수를 방지할 수 있다.
- 백그라운드 작업의 안정성을 확보할 수 있다.

### CompositionLocal을 통해 의존성 전파를 최적화한다.
상세 방식:
- 모든 하위 컴포넌트에 넘겨줘야 하는 전역적 정보(UserSession, Theme 등)는 매개변수가 아닌 `CompositionLocal`을 통해 암시적으로 전달한다.
- 전역적으로 공유되는 테마, 세션 외의 비즈니스 데이터는 가급적 명시적 매개변수(Hoisting)를 통해 전달한다. 

효과:
- 함수 시그니처가 간결해진다.
- 중간 단계의 Composable들이 불필요한 데이터를 전달하지 않아도 된다.

