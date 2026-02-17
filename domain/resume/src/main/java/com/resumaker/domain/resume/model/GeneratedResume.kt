package com.resumaker.domain.resume.model

/**
 * 이력서 생성 API 응답 도메인 모델.
 */
data class GeneratedResume(
    val resumeId: Int,
    val createdAt: String,
    val updatedAt: String,
    val itemList: List<Int>? = null,
    val items: List<GeneratedResumeItem>? = null
)

data class GeneratedResumeItem(
    val elementId: Int,
    val type: String,
    val subTitle: String? = null,
    val content: String? = null
)
