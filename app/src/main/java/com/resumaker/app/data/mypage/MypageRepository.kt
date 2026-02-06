package com.resumaker.app.data.mypage

import com.resumaker.app.data.remote.ApiResult
import com.resumaker.app.data.remote.dto.mypage.AwardDto
import com.resumaker.app.data.remote.dto.mypage.CertificationDto
import com.resumaker.app.data.remote.dto.mypage.EducationDto
import com.resumaker.app.data.remote.dto.mypage.MypageResponse
import com.resumaker.app.data.remote.dto.mypage.MypageUpdateRequest
import com.resumaker.app.data.remote.dto.mypage.WorkExperienceDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
// import retrofit2.HttpException
// import java.io.IOException

/** [임시 Mock] 마이페이지 목 데이터 */
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

/**
 * 마이페이지(이력서 정보) 조회·수정 데이터 소스 진입점.
 */
class MypageRepository(
    private val mypageApi: MypageApiService
) {

    /**
     * 마이페이지 정보를 조회합니다.
     * [임시] API 호출 대신 Mock 데이터 반환.
     */
    suspend fun getMypage(): ApiResult<MypageResponse> = withContext(Dispatchers.IO) {
        // try { val response = mypageApi.getMypage(); ApiResult.Success(response) } catch (e: HttpException) { ... }
        ApiResult.Success(mockMypageResponse())
    }

    /**
     * 마이페이지 정보를 수정합니다.
     * [임시] API 호출 대신 Mock 데이터 그대로 반환.
     */
    suspend fun updateMypage(request: MypageUpdateRequest): ApiResult<MypageResponse> = withContext(Dispatchers.IO) {
        // try { val response = mypageApi.updateMypage(request); ApiResult.Success(response) } catch (e: HttpException) { ... }
        ApiResult.Success(mockMypageResponse())
    }
}
