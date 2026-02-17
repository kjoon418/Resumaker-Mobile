package com.resumaker.feature.resumebuilder.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.resumaker.domain.persona.model.Persona
import com.resumaker.feature.resumebuilder.contract.ResumeEditState

/** ResumeEditState - Empty, Loading, Error, Success 상태 */
class ResumeEditStateProvider : PreviewParameterProvider<ResumeEditState> {
    override val values: Sequence<ResumeEditState> = sequenceOf(
        ResumeEditState(),
        ResumeEditState(isLoadingPersonas = true),
        ResumeEditState(personasError = "네트워크 연결을 확인해 주세요."),
        ResumeEditState(
            personas = listOf(
                Persona("1", "친절한 면접관", "많은 피드백을 주며 대화를 이끌어 줍니다.", "persona1", "", "2025.02.05"),
                Persona("2", "날카로운 면접관", "깊이 있는 기술 질문을 주로 합니다.", "persona2", "", "2025.02.03")
            )
        )
    )
}
