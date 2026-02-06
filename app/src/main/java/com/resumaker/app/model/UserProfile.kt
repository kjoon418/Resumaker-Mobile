package com.resumaker.app.model

/**
 * 마이페이지 기본 정보. 서버 데이터와 매핑용.
 * @param gender MALE, FEMALE
 */
data class UserProfile(
    val name: String,
    val email: String,
    val age: Int,
    val gender: String,
    val job: String,
    val phoneNumber: String
)

/** 학력 사항 */
data class Education(
    val school: String,
    val major: String,
    val period: String,
    val score: String
)

/** 자격증 */
data class Certification(
    val name: String,
    val issuer: String,
    val date: String,
    val score: String? = null
)

/** 수상 경력 */
data class Award(
    val title: String,
    val content: String,
    val year: String,
    val issuer: String
)

/** 경력 사항 */
data class Experience(
    val company: String,
    val role: String,
    val period: String,
    val description: String,
    val isCurrent: Boolean
)
