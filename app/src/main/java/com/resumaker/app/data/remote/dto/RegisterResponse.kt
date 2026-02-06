package com.resumaker.app.data.remote.dto

import com.google.gson.annotations.SerializedName

/**
 * POST /api/users/register/ 201 응답 Body.
 */
data class RegisterResponse(
    @SerializedName("message") val message: String,
    @SerializedName("user") val user: UserDto
)
