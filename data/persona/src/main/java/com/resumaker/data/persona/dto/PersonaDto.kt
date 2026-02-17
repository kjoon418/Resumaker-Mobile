package com.resumaker.data.persona.dto

import com.google.gson.annotations.SerializedName

data class PersonaDto(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String,
    @SerializedName("prompt") val prompt: String,
    @SerializedName("user_id") val userId: Int?,
    @SerializedName("username") val username: String?,
    @SerializedName("is_default") val isDefault: Boolean,
    @SerializedName("is_active") val isActive: Boolean,
    @SerializedName("created_at") val createdAt: String?,
    @SerializedName("updated_at") val updatedAt: String?
)
