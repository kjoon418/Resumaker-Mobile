package com.resumaker.app.model

/**
 * 이력서 상세 입력 화면에서 사용하는 "추가 정보" 항목.
 */
data class ExtraInfoItem(
    val id: String,
    val title: String,
    val content: String
)

/**
 * 이력서 상세 입력 화면에서 사용하는 "프로젝트 이력" 항목.
 */
data class ProjectHistoryItem(
    val id: String,
    val projectName: String,
    val keyTasks: String
)
