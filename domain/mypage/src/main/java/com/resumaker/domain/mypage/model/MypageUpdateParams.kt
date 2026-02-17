package com.resumaker.domain.mypage.model

/**
 * 마이페이지 수정 요청 도메인 모델.
 */
data class MypageUpdateParams(
    val educations: List<EducationRequest>,
    val awards: List<AwardRequest>,
    val certifications: List<CertificationRequest>,
    val workExperiences: List<WorkExperienceRequest>
)

data class EducationRequest(
    val university: String,
    val major: String,
    val enrollmentYear: Long,
    val graduationYear: Long,
    val gpa: String
)

data class AwardRequest(
    val competitionName: String,
    val awardTitle: String,
    val awardYear: Long,
    val awardingOrganization: String
)

data class CertificationRequest(
    val name: String,
    val obtainedDate: String,
    val issuingOrganization: String,
    val score: Long,
    val grade: String
)

data class WorkExperienceRequest(
    val companyName: String,
    val startYear: Long,
    val endYear: Long,
    val jobTitle: String,
    val jobDescription: String
)
