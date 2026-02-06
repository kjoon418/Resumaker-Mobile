package com.resumaker.app.model

/**
 * PDF 파싱 결과로 이력서 상세 입력 폼을 채우기 위한 초기값.
 * API 응답을 변환한 도메인 모델이며, 비어 있는 경우(건너뛰기)는 null 또는 빈 값으로 처리.
 */
data class ParsedResumeDetail(
    val resumeFormat: String = "",
    val targetRole: String = "",
    val headline: String = "",
    val strengthKeywords: List<String> = emptyList(),
    val projects: List<ProjectHistoryItem> = emptyList(),
    val collaborationStyle: String = "",
    val mainTechStack: List<String> = emptyList(),
    val futureGoal: String = ""
)
