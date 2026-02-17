package com.resumaker.feature.careermanager.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.resumaker.core.common.model.Resume

/** Resume 목록 - Empty, Single, Multiple */
class ResumeListProvider : PreviewParameterProvider<List<Resume>> {
    override val values: Sequence<List<Resume>> = sequenceOf(
        emptyList(),
        listOf(Resume("1", "프론트엔드 개발자 이력서", "2025.02.05", "doc")),
        listOf(
            Resume("1", "프론트엔드 개발자 이력서", "2025.02.05", "doc"),
            Resume("2", "백엔드 개발자 지원용", "2025.02.01", "doc"),
            Resume("3", "풀스택 포트폴리오", "2025.01.28", "doc")
        )
    )
}
