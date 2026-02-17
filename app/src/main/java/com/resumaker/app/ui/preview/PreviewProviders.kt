package com.resumaker.app.ui.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.resumaker.domain.persona.model.Persona
import com.resumaker.core.common.model.Resume

/**
 * PreviewParameterProvider 구현.
 * app 모듈의 Screen들은 feature로 이동했으므로, 공통으로 사용할 수 있는 Provider만 유지합니다.
 */

/** Resume 단일 - 다양한 제목/아이콘 타입 */
class ResumeProvider : PreviewParameterProvider<Resume> {
    override val values: Sequence<Resume> = sequenceOf(
        Resume("1", "프론트엔드 개발자 이력서", "2025.02.05", "doc"),
        Resume("2", "이름이 긴 이력서 예시를 한번 만들어보겠습니다", "2025.02.01", "code"),
        Resume("3", "백엔드 개발자 지원용", "2025.01.28", "doc")
    )
}

/** Persona 단일 - 다양한 페르소나 타입 */
class PersonaProvider : PreviewParameterProvider<Persona> {
    override val values: Sequence<Persona> = sequenceOf(
        Persona("1", "친절한 면접관", "많은 피드백을 주며 대화를 이끌어 줍니다.", "persona1", "", "2025.02.05"),
        Persona("2", "날카로운 면접관", "깊이 있는 기술 질문을 주로 합니다.", "persona2", "", "2025.02.03"),
        Persona("3", "비즈니스 관점 면접관", "비즈니스 임팩트와 협업 경험을 묻습니다.", "persona3", "", "2025.02.01")
    )
}
