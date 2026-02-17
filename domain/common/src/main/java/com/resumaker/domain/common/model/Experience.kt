package com.resumaker.domain.common.model

/** 경력 사항 */
data class Experience(
    val company: String,
    val role: String,
    val period: String,
    val description: String,
    val isCurrent: Boolean
)
