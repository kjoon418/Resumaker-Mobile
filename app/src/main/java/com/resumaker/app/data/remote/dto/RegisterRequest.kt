package com.resumaker.app.data.remote.dto

import com.google.gson.annotations.SerializedName

/**
 * POST /api/users/register/ 요청 Body.
 * 작업 ID: users_register_create
 * 비밀번호 8자 이상, 성별은 M/F/O.
 */
data class RegisterRequest(
    @SerializedName("username") val username: String,
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String,
    @SerializedName("name") val name: String,
    @SerializedName("age") val age: Int,
    @SerializedName("gender") val gender: String,
    @SerializedName("job") val job: String,
    @SerializedName("phone_number") val phoneNumber: String
)
