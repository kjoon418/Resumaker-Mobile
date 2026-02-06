package com.resumaker.app.data.remote.dto

import com.google.gson.annotations.SerializedName

/**
 * POST /api/users/login/ 요청 Body.
 * 서버는 username 필드를 사용합니다. (이메일 로그인 시 email 값을 username으로 전달)
 */
data class LoginRequest(
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String
)
