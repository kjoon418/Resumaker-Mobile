package com.resumaker.domain.common.model

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
