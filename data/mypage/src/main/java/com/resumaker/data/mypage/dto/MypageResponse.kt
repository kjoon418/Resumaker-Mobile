package com.resumaker.data.mypage.dto

import com.google.gson.annotations.SerializedName

data class MypageResponse(
    @SerializedName("educations") val educations: List<EducationDto>,
    @SerializedName("awards") val awards: List<AwardDto>,
    @SerializedName("certifications") val certifications: List<CertificationDto>,
    @SerializedName("work_experiences") val workExperiences: List<WorkExperienceDto>
)

data class EducationDto(
    @SerializedName("id") val id: Int,
    @SerializedName("university") val university: String,
    @SerializedName("major") val major: String,
    @SerializedName("enrollment_year") val enrollmentYear: Long,
    @SerializedName("graduation_year") val graduationYear: Long,
    @SerializedName("gpa") val gpa: String
)

data class AwardDto(
    @SerializedName("id") val id: Int,
    @SerializedName("competition_name") val competitionName: String,
    @SerializedName("award_title") val awardTitle: String,
    @SerializedName("award_year") val awardYear: Long,
    @SerializedName("awarding_organization") val awardingOrganization: String
)

data class CertificationDto(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("obtained_date") val obtainedDate: String,
    @SerializedName("issuing_organization") val issuingOrganization: String,
    @SerializedName("score") val score: Long,
    @SerializedName("grade") val grade: String
)

data class WorkExperienceDto(
    @SerializedName("id") val id: Int,
    @SerializedName("company_name") val companyName: String,
    @SerializedName("start_year") val startYear: Long,
    @SerializedName("end_year") val endYear: Long,
    @SerializedName("job_title") val jobTitle: String,
    @SerializedName("job_description") val jobDescription: String
)
