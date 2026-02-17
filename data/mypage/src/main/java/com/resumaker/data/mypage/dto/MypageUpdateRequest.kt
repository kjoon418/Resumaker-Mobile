package com.resumaker.data.mypage.dto

import com.google.gson.annotations.SerializedName

data class MypageUpdateRequest(
    @SerializedName("educations") val educations: List<EducationRequest>,
    @SerializedName("awards") val awards: List<AwardRequest>,
    @SerializedName("certifications") val certifications: List<CertificationRequest>,
    @SerializedName("work_experiences") val workExperiences: List<WorkExperienceRequest>
)

data class EducationRequest(
    @SerializedName("university") val university: String,
    @SerializedName("major") val major: String,
    @SerializedName("enrollment_year") val enrollmentYear: Long,
    @SerializedName("graduation_year") val graduationYear: Long,
    @SerializedName("gpa") val gpa: String
)

data class AwardRequest(
    @SerializedName("competition_name") val competitionName: String,
    @SerializedName("award_title") val awardTitle: String,
    @SerializedName("award_year") val awardYear: Long,
    @SerializedName("awarding_organization") val awardingOrganization: String
)

data class CertificationRequest(
    @SerializedName("name") val name: String,
    @SerializedName("obtained_date") val obtainedDate: String,
    @SerializedName("issuing_organization") val issuingOrganization: String,
    @SerializedName("score") val score: Long,
    @SerializedName("grade") val grade: String
)

data class WorkExperienceRequest(
    @SerializedName("company_name") val companyName: String,
    @SerializedName("start_year") val startYear: Long,
    @SerializedName("end_year") val endYear: Long,
    @SerializedName("job_title") val jobTitle: String,
    @SerializedName("job_description") val jobDescription: String
)
