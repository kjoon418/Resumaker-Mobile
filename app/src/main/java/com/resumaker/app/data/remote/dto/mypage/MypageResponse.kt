package com.resumaker.app.data.remote.dto.mypage

import com.google.gson.annotations.SerializedName

/**
 * GET /api/resume/mypage 응답 Body.
 * PUT /api/resume/mypage 응답 Body와 동일한 구조.
 */
data class MypageResponse(
    @SerializedName("educations") val educations: List<EducationDto>,
    @SerializedName("awards") val awards: List<AwardDto>,
    @SerializedName("certifications") val certifications: List<CertificationDto>,
    @SerializedName("work_experiences") val workExperiences: List<WorkExperienceDto>
)

/**
 * 학력 항목 (응답용, id 포함).
 */
data class EducationDto(
    @SerializedName("id") val id: Int,
    @SerializedName("university") val university: String,
    @SerializedName("major") val major: String,
    @SerializedName("enrollment_year") val enrollmentYear: Long,
    @SerializedName("graduation_year") val graduationYear: Long,
    @SerializedName("gpa") val gpa: String
)

/**
 * 수상 항목 (응답용, id 포함).
 */
data class AwardDto(
    @SerializedName("id") val id: Int,
    @SerializedName("competition_name") val competitionName: String,
    @SerializedName("award_title") val awardTitle: String,
    @SerializedName("award_year") val awardYear: Long,
    @SerializedName("awarding_organization") val awardingOrganization: String
)

/**
 * 자격증 항목 (응답용, id 포함).
 */
data class CertificationDto(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("obtained_date") val obtainedDate: String,
    @SerializedName("issuing_organization") val issuingOrganization: String,
    @SerializedName("score") val score: Long,
    @SerializedName("grade") val grade: String
)

/**
 * 경력 항목 (응답용, id 포함).
 */
data class WorkExperienceDto(
    @SerializedName("id") val id: Int,
    @SerializedName("company_name") val companyName: String,
    @SerializedName("start_year") val startYear: Long,
    @SerializedName("end_year") val endYear: Long,
    @SerializedName("job_title") val jobTitle: String,
    @SerializedName("job_description") val jobDescription: String
)
