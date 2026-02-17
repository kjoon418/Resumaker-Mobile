package com.resumaker.feature.careermanager.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.resumaker.domain.persona.model.Persona
import com.resumaker.core.common.model.Resume

/** CareerManagerScreen - resumes + personas 조합 */
data class CareerManagerPreviewData(val resumes: List<Resume>, val personas: List<Persona>)

class CareerManagerPreviewProvider : PreviewParameterProvider<CareerManagerPreviewData> {
    override val values: Sequence<CareerManagerPreviewData> = sequenceOf(
        CareerManagerPreviewData(emptyList(), emptyList()),
        CareerManagerPreviewData(
            listOf(
                Resume("1", "프론트엔드 개발자 이력서", "2025.02.05", "doc"),
                Resume("2", "백엔드 개발자 지원용", "2025.02.01", "doc"),
                Resume("3", "풀스택 포트폴리오", "2025.01.28", "doc")
            ),
            listOf(
                Persona("1", "친절한 면접관", "많은 피드백을 주며 대화를 이끌어 줍니다.", "persona1", "", "2025.02.05"),
                Persona("2", "날카로운 면접관", "깊이 있는 기술 질문을 주로 합니다.", "persona2", "", "2025.02.03"),
                Persona("3", "비즈니스 관점 면접관", "비즈니스 임팩트와 협업 경험을 묻습니다.", "persona3", "", "2025.02.01")
            )
        )
    )
}
