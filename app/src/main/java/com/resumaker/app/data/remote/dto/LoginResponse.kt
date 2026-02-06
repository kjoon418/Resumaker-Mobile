package com.resumaker.app.data.remote.dto

import com.google.gson.annotations.SerializedName

/**
 * POST /api/users/login/ 200 응답 Body.
 */
data class LoginResponse(
    @SerializedName("message") val message: String,
    @SerializedName("user") val user: UserDto
)

/**
 * 로그인 응답 내 사용자 상세 정보.
 * 서버 snake_case 필드를 Gson으로 매핑합니다.
 */
data class UserDto(
    @SerializedName("id") val id: Int,
    @SerializedName("username") val username: String,
    @SerializedName("email") val email: String,
    @SerializedName("name") val name: String,
    @SerializedName("age") val age: Int,
    @SerializedName("gender") val gender: String,
    @SerializedName("job") val job: String,
    @SerializedName("phone_number") val phoneNumber: String,
    @SerializedName("date_joined") val dateJoined: String?
)
