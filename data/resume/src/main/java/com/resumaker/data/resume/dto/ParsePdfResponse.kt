package com.resumaker.data.resume.dto

import com.google.gson.annotations.SerializedName

data class ParsePdfResponse(
    @SerializedName("resume_format") val resumeFormat: String? = null,
    @SerializedName("target_role") val targetRole: String? = null,
    @SerializedName("headline") val headline: String? = null,
    @SerializedName("strength_keywords") val strengthKeywords: List<String>? = null,
    @SerializedName("projects") val projects: List<ParsePdfProject>? = null,
    @SerializedName("collaboration_style") val collaborationStyle: String? = null,
    @SerializedName("main_tech_stack") val mainTechStack: List<String>? = null,
    @SerializedName("future_goal") val futureGoal: String? = null
)

data class ParsePdfProject(
    @SerializedName("start") val start: String? = null,
    @SerializedName("end") val end: String? = null,
    @SerializedName("title") val title: String? = null,
    @SerializedName("bullets") val bullets: List<String>? = null,
    @SerializedName("tech_stack") val techStack: List<String>? = null
)
