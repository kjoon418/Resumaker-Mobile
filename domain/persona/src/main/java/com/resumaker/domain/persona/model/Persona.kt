package com.resumaker.domain.persona.model

/**
 * 면접관 페르소나 도메인 모델.
 * @param iconType 아이콘 타입 문자열 (UI에서 IconType.fromString으로 변환)
 * @param lastModified 최근 수정일 (페르소나 관리 화면 등에서 표시)
 */
data class Persona(
    val id: String,
    val title: String,
    val description: String,
    val iconType: String = "warm",
    val prompt: String,
    val lastModified: String = ""
)
