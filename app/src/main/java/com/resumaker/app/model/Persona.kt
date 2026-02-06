package com.resumaker.app.model

/**
 * 면접관 페르소나 목록/카드에 표시할 데이터.
 * @param iconType 아이콘 타입 식별자 (추후 아이콘 매핑에 사용)
 */
data class Persona(
    val id: String,
    val title: String,
    val description: String,
    val iconType: String
)
