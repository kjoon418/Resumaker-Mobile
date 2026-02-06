package com.resumaker.app.model

/**
 * 면접관 페르소나 목록/카드에 표시할 데이터.
 * @param iconType 아이콘 타입 식별자 (추후 아이콘 매핑에 사용)
 * @param lastModified 최근 수정일 (페르소나 관리 화면 등에서 표시)
 */
data class Persona(
    val id: String,
    val title: String,
    val description: String,
    val iconType: String,
    val prompt: String,
    val lastModified: String = ""
)
