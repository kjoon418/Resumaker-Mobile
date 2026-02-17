package com.resumaker.data.mypage

import com.resumaker.domain.common.ApiResult
import com.resumaker.domain.common.model.Award
import com.resumaker.domain.common.model.Certification
import com.resumaker.domain.common.model.Education
import com.resumaker.domain.common.model.Experience
import com.resumaker.data.mypage.dto.AwardDto
import com.resumaker.data.mypage.dto.CertificationDto
import com.resumaker.data.mypage.dto.EducationDto
import com.resumaker.data.mypage.dto.MypageResponse
import com.resumaker.data.mypage.dto.WorkExperienceDto
import com.resumaker.domain.mypage.MypageRepository
import com.resumaker.domain.mypage.model.MypageData
import com.resumaker.domain.mypage.model.MypageUpdateParams
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

private const val CURRENT_YEAR_SENTINEL = 9999L

private fun mockMypageResponse(): MypageResponse = MypageResponse(
    educations = listOf(
        EducationDto(1, "서울대학교", "컴퓨터공학과", 2014L, 2018L, "4.2/4.5")
    ),
    awards = listOf(
        AwardDto(1, "연간 성과 평가 우수", "우수 사원상", 2023L, "테크컴퍼니")
    ),
    certifications = listOf(
        CertificationDto(1, "정보처리기사", "2020.05", "한국산업인력공단", 0L, "1차 합격"),
        CertificationDto(2, "TOEIC", "2019.03", "ETS", 920L, "")
    ),
    workExperiences = listOf(
        WorkExperienceDto(1, "(주)테크컴퍼니", 2022L, 9999L, "시니어 프론트엔드 개발자", "React 기반 웹 서비스 개발 및 팀 리드. 성능 최적화 및 디자인 시스템 구축."),
        WorkExperienceDto(2, "이전 회사", 2018L, 2021L, "주니어 개발자", "웹 프론트엔드 개발 및 유지보수.")
    )
)

class MypageRepositoryImpl(
    private val mypageApi: MypageApiService
) : MypageRepository {

    override suspend fun getMypage(): ApiResult<MypageData> = withContext(Dispatchers.IO) {
        val response = mockMypageResponse()
        ApiResult.Success(response.toDomain())
    }

    override suspend fun updateMypage(params: MypageUpdateParams): ApiResult<MypageData> =
        withContext(Dispatchers.IO) {
            val response = mockMypageResponse()
            ApiResult.Success(response.toDomain())
        }
}

private fun MypageResponse.toDomain(): MypageData = MypageData(
    educations = educations.map { it.toEducation() },
    awards = awards.map { it.toAward() },
    certifications = certifications.map { it.toCertification() },
    workExperiences = workExperiences.map { it.toExperience() }
)

private fun EducationDto.toEducation() = Education(
    school = university,
    major = major,
    period = "${enrollmentYear} ~ ${graduationYear}",
    score = gpa
)

private fun WorkExperienceDto.toExperience(): Experience {
    val isCurrent = endYear >= CURRENT_YEAR_SENTINEL
    val endDisplay = if (isCurrent) "현재" else endYear.toString()
    return Experience(
        company = companyName,
        role = jobTitle,
        period = "${startYear} ~ ${endDisplay}",
        description = jobDescription,
        isCurrent = isCurrent
    )
}

private fun CertificationDto.toCertification() = Certification(
    name = name,
    issuer = issuingOrganization,
    date = obtainedDate,
    score = grade.takeIf { it.isNotBlank() } ?: score.takeIf { it > 0 }?.toString()
)

private fun AwardDto.toAward() = Award(
    title = awardTitle,
    content = competitionName,
    year = awardYear.toString(),
    issuer = awardingOrganization
)
