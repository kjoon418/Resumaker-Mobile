package com.resumaker.data.persona.dto

import com.google.gson.annotations.SerializedName

data class CreatePersonaRequest(
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String,
    @SerializedName("prompt") val prompt: String,
    @SerializedName("is_active") val isActive: Boolean = true
)
