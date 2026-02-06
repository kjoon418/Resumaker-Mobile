package com.resumaker.app.data.remote.dto

import com.google.gson.annotations.SerializedName

/**
 * POST /api/personas/ 요청 Body.
 * 작업 ID: personas_create
 */
data class CreatePersonaRequest(
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String,
    @SerializedName("prompt") val prompt: String,
    @SerializedName("is_active") val isActive: Boolean = true
)
