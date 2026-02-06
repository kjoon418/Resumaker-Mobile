package com.resumaker.app.data.remote.dto.mypage

import com.google.gson.annotations.SerializedName

/**
 * PUT /api/resume/mypage 요청 Body.
 * 각 항목에는 id가 없습니다.
 */
data class MypageUpdateRequest(
    @SerializedName("educations") val educations: List<EducationRequest>,
    @SerializedName("awards") val awards: List<AwardRequest>,
    @SerializedName("certifications") val certifications: List<CertificationRequest>,
    @SerializedName("work_experiences") val workExperiences: List<WorkExperienceRequest>
)

/**
 * 학력 항목 (수정 요청용, id 없음).
 */
data class EducationRequest(
    @SerializedName("university") val university: String,
    @SerializedName("major") val major: String,
    @SerializedName("enrollment_year") val enrollmentYear: Long,
    @SerializedName("graduation_year") val graduationYear: Long,
    @SerializedName("gpa") val gpa: String
)

/**
 * 수상 항목 (수정 요청용, id 없음).
 */
data class AwardRequest(
    @SerializedName("competition_name") val competitionName: String,
    @SerializedName("award_title") val awardTitle: String,
    @SerializedName("award_year") val awardYear: Long,
    @SerializedName("awarding_organization") val awardingOrganization: String
)

/**
 * 자격증 항목 (수정 요청용, id 없음).
 */
data class CertificationRequest(
    @SerializedName("name") val name: String,
    @SerializedName("obtained_date") val obtainedDate: String,
    @SerializedName("issuing_organization") val issuingOrganization: String,
    @SerializedName("score") val score: Long,
    @SerializedName("grade") val grade: String
)

/**
 * 경력 항목 (수정 요청용, id 없음).
 */
data class WorkExperienceRequest(
    @SerializedName("company_name") val companyName: String,
    @SerializedName("start_year") val startYear: Long,
    @SerializedName("end_year") val endYear: Long,
    @SerializedName("job_title") val jobTitle: String,
    @SerializedName("job_description") val jobDescription: String
)
