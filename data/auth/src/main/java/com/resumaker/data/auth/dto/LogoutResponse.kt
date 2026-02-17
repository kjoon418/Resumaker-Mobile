package com.resumaker.data.auth.dto

import com.google.gson.annotations.SerializedName

data class LogoutResponse(
    @SerializedName("message") val message: String
)
