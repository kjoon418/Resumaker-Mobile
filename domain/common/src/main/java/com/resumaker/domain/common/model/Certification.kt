package com.resumaker.domain.common.model

/** 자격증 */
data class Certification(
    val name: String,
    val issuer: String,
    val date: String,
    val score: String? = null
)
