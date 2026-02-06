package com.resumaker.app.data.remote.dto

import com.google.gson.annotations.SerializedName

/**
 * POST /api/users/logout/ 200 응답 Body.
 */
data class LogoutResponse(
    @SerializedName("message") val message: String
)
