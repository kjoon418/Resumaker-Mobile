package com.resumaker.app.model

/**
 * 이력서 목록/카드에 표시할 이력서 데이터.
 * @param iconType 아이콘 타입 식별자 (추후 아이콘 매핑에 사용)
 */
data class Resume(
    val id: String,
    val title: String,
    val lastModified: String,
    val iconType: String
)
