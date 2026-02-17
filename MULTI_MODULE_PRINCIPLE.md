# 멀티 모듈 원칙

## 1. 의존성 단방향 원칙 (Dependency Rule)

Clean Architecture의 핵심입니다. 의존성은 반드시 **고수준(추상)에서 저수준(구체)**으로, 즉 **Outer(UI/Data)에서 Inner(Domain)**로만 흘러야 합니다.

* **규정:** `domain` 모듈은 그 어떤 모듈도 의존해서는 안 됩니다. (순수 Kotlin/Java 모듈 유지)
* **원칙:** `data`와 `feature` 모듈은 `domain`을 의존하지만, `domain`은 자신이 누구에게 쓰이는지 몰라야 합니다.
* **KMP 이점:** 이렇게 분리된 `domain`은 iOS에서도 수정 없이 즉시 컴파일됩니다.

---

## 2. 인터페이스 격리 및 DIP (Dependency Inversion Principle)

모듈 간의 결합을 끊기 위해 **'인터페이스는 도메인에, 구현체는 데이터에'** 두는 원칙을 고수해야 합니다.

* **규정:** `domain` 모듈에 `Repository` 인터페이스를 정의하고, `data` 모듈에서 이를 구현(Implementation)합니다.
* **원칙:** `feature` 모듈은 `domain`의 인터페이스(UseCase)만 바라봅니다. 실제 데이터가 Retrofit에서 오는지, 로컬 DB에서 오는지 `feature`는 알 필요가 없습니다.
* **천재적 포인트:** 이 구조를 지키면 테스트 시 `Repository`의 Mock 객체를 주입하기 매우 쉬워집니다.

---

## 3. 모듈 가시성 제어 (Encapsulation)

모듈 내부의 구현 상세를 외부(다른 모듈)에 노출하지 마세요.

* **규정:** `internal` 키워드를 적극 활용하십시오. 외부 모듈에서 호출할 필요가 없는 DTO, Mapper, 내부 로직은 모두 `internal`로 선언합니다.
* **원칙:** 각 모듈은 명확한 **Public API(Entry Point)**만 노출합니다.
* **효과:** 한 모듈의 내부 코드를 수정해도 다른 모듈의 컴파일에 영향을 주지 않아 빌드 속도가 최적화됩니다.

---

## 4. 공통 모듈의 비대화 방지 (The 'Common' Trap)

모든 모듈이 사용하는 `core`나 `common` 모듈이 **"쓰레기통"**이 되지 않게 경계해야 합니다.

* **규정:** `core` 모듈이 커지면 모든 모듈이 재컴파일되는 '빌드 병목'이 발생합니다.
* **원칙:** `core:ui`, `core:network`, `core:database`처럼 성격에 맞게 잘게 쪼개세요.
* **해결 공식:** 상호작용 계수를 낮추기 위해, 한 모듈이 수정될 때 영향을 받는 범위(Blast Radius)를 최소화해야 합니다.

---

## 5. 피처 간 독립성 (Feature Independence)

피처 모듈끼리는 서로 참조하지 않는 것을 원칙으로 합니다. (No Direct Link between Features)

* **규정:** `feature:login` 모듈이 `feature:resume` 모듈을 직접 의존하게 하지 마세요.
* **원칙:** 화면 이동(Navigation)은 `app` 모듈이나 별도의 `navigator` 모듈을 통해 중재(Mediation)합니다.
* **이유:** 피처 간 결합도가 낮아야 특정 피처만 떼어내서 테스트하거나, 다른 프로젝트로 이식하기 쉽습니다.
