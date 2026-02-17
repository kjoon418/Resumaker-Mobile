package com.resumaker.domain.resume.model

/**
 * 이력서 생성 요청 도메인 모델.
 */
data class GenerateResumeParams(
    val resumeFormat: String? = null,
    val targetRole: String? = null,
    val headline: String? = null,
    val strengthKeywords: List<String>? = null,
    val projects: List<GenerateResumeProjectItem>? = null,
    val collaborationStyle: String? = null,
    val mainTechStack: List<String>? = null,
    val futureGoal: String? = null
)

data class GenerateResumeProjectItem(
    val title: String? = null,
    val bullets: List<String>? = null
)
