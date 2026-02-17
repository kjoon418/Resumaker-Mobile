package com.resumaker.feature.careermanager.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.resumaker.domain.persona.model.Persona

/** Persona 목록 - Empty, Single, Multiple */
class PersonaListProvider : PreviewParameterProvider<List<Persona>> {
    override val values: Sequence<List<Persona>> = sequenceOf(
        emptyList(),
        listOf(Persona("1", "친절한 면접관", "많은 피드백을 주며 대화를 이끌어 줍니다.", "persona1", "", "2025.02.05")),
        listOf(
            Persona("1", "친절한 면접관", "많은 피드백을 주며 대화를 이끌어 줍니다.", "persona1", "", "2025.02.05"),
            Persona("2", "날카로운 면접관", "깊이 있는 기술 질문을 주로 합니다.", "persona2", "", "2025.02.03"),
            Persona("3", "비즈니스 관점 면접관", "비즈니스 임팩트와 협업 경험을 묻습니다.", "persona3", "", "2025.02.01")
        )
    )
}
