package com.resumaker.domain.resume.model

/**
 * 이력서 상세 입력 화면에서 사용하는 "프로젝트 이력" 항목.
 */
data class ProjectHistoryItem(
    val id: String,
    val projectName: String,
    val keyTasks: String
)
