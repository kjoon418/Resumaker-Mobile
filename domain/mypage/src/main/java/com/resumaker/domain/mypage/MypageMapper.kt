package com.resumaker.domain.mypage

import com.resumaker.domain.common.model.Award
import com.resumaker.domain.common.model.Certification
import com.resumaker.domain.common.model.Education
import com.resumaker.domain.common.model.Experience
import com.resumaker.domain.mypage.model.EducationRequest
import com.resumaker.domain.mypage.model.MypageData
import com.resumaker.domain.mypage.model.MypageUpdateParams
import com.resumaker.domain.mypage.model.WorkExperienceRequest
import com.resumaker.domain.mypage.model.AwardRequest
import com.resumaker.domain.mypage.model.CertificationRequest

private const val CURRENT_YEAR_SENTINEL = 9999L

/**
 * MypageData를 MypageUpdateParams로 변환.
 * period 형식: "YYYY ~ YYYY" 또는 "YYYY ~ 현재"
 */
fun MypageData.toUpdateParams(): MypageUpdateParams = MypageUpdateParams(
    educations = educations.map { it.toEducationRequest() },
    awards = awards.map { it.toAwardRequest() },
    certifications = certifications.map { it.toCertificationRequest() },
    workExperiences = workExperiences.map { it.toWorkExperienceRequest() }
)

private fun Education.toEducationRequest(): EducationRequest {
    val (start, end) = parsePeriod(period)
    return EducationRequest(
        university = school,
        major = major,
        enrollmentYear = start,
        graduationYear = end,
        gpa = score
    )
}

private fun Award.toAwardRequest() = AwardRequest(
    competitionName = content,
    awardTitle = title,
    awardYear = year.toLongOrNull() ?: 0L,
    awardingOrganization = issuer
)

private fun Certification.toCertificationRequest() = CertificationRequest(
    name = name,
    obtainedDate = date,
    issuingOrganization = issuer,
    score = score?.toLongOrNull() ?: 0L,
    grade = score ?: ""
)

private fun Experience.toWorkExperienceRequest(): WorkExperienceRequest {
    val (start, end) = parsePeriod(period)
    return WorkExperienceRequest(
        companyName = company,
        startYear = start,
        endYear = end,
        jobTitle = role,
        jobDescription = description
    )
}

private fun parsePeriod(period: String): Pair<Long, Long> {
    val parts = period.split("~").map { it.trim() }
    val start = parts.getOrNull(0)?.takeWhile { it.isDigit() }?.toLongOrNull() ?: 0L
    val endPart = parts.getOrNull(1) ?: ""
    val end = if (endPart == "현재") CURRENT_YEAR_SENTINEL else endPart.takeWhile { it.isDigit() }.toLongOrNull() ?: start
    return start to end
}
