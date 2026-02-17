package com.resumaker.data.resume.dto

import com.google.gson.annotations.SerializedName

data class GenerateResumeResponse(
    @SerializedName("resume_id") val resumeId: Int,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("updated_at") val updatedAt: String,
    @SerializedName("item_list") val itemList: List<Int>? = null,
    @SerializedName("items") val items: List<GeneratedResumeItem>? = null
)

data class GeneratedResumeItem(
    @SerializedName("element_id") val elementId: Int,
    @SerializedName("type") val type: String,
    @SerializedName("sub_title") val subTitle: String? = null,
    @SerializedName("content") val content: String? = null
)
