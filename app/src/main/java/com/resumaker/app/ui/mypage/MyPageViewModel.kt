package com.resumaker.app.ui.mypage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.resumaker.app.data.mypage.MypageRepository
import com.resumaker.app.data.remote.ApiResult
import com.resumaker.app.data.remote.dto.mypage.AwardDto
import com.resumaker.app.data.remote.dto.mypage.CertificationDto
import com.resumaker.app.data.remote.dto.mypage.EducationDto
import com.resumaker.app.data.remote.dto.mypage.MypageResponse
import com.resumaker.app.data.remote.dto.mypage.MypageUpdateRequest
import com.resumaker.app.data.remote.dto.mypage.WorkExperienceDto
import com.resumaker.app.model.Award
import com.resumaker.app.model.Certification
import com.resumaker.app.model.Education
import com.resumaker.app.model.Experience
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/** 재직 중 판단용: end_year가 이 값 이상이면 "재직 중"으로 간주 */
private const val CURRENT_YEAR_SENTINEL = 9999L

/**
 * 마이페이지 화면의 상태 및 로직.
 * MypageRepository를 통해 조회·수정하고, UI 모델로 변환해 노출합니다.
 */
class MyPageViewModel(
    private val mypageRepository: MypageRepository
) : ViewModel() {

    private val _mypageData = MutableStateFlow<MypageResponse?>(null)
    val mypageData: StateFlow<MypageResponse?> = _mypageData.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _isSaving = MutableStateFlow(false)
    val isSaving: StateFlow<Boolean> = _isSaving.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    /** 로컬에서 수정이 있어 저장이 필요한 경우 true (추후 편집 UI 연동 시 사용) */
    private val _hasChanges = MutableStateFlow(false)
    val hasChanges: StateFlow<Boolean> = _hasChanges.asStateFlow()

    init {
        loadMypage()
    }

    fun loadMypage() {
        viewModelScope.launch {
            _isLoading.update { true }
            _errorMessage.update { null }
            when (val result = mypageRepository.getMypage()) {
                is ApiResult.Success -> _mypageData.update { result.data }
                is ApiResult.Error -> _errorMessage.update { result.message }
                is ApiResult.NetworkError -> _errorMessage.update { "네트워크 연결을 확인해 주세요." }
            }
            _isLoading.update { false }
        }
    }

    /**
     * 현재 마이페이지 데이터를 서버에 저장합니다.
     * 저장 후 응답으로 상태를 갱신합니다.
     */
    fun saveMypage() {
        val current = _mypageData.value ?: return
        viewModelScope.launch {
            _isSaving.update { true }
            _errorMessage.update { null }
            val request = current.toUpdateRequest()
            when (val result = mypageRepository.updateMypage(request)) {
                is ApiResult.Success -> {
                    _mypageData.update { result.data }
                    _hasChanges.update { false }
                }
                is ApiResult.Error -> _errorMessage.update { result.message }
                is ApiResult.NetworkError -> _errorMessage.update { "네트워크 연결을 확인해 주세요." }
            }
            _isSaving.update { false }
        }
    }

    fun clearErrorMessage() {
        _errorMessage.update { null }
    }

    /** DTO 목록을 UI용 [Education] 목록으로 변환 */
    fun educationsForUi(data: MypageResponse?): List<Education> {
        if (data == null) return emptyList()
        return data.educations.map { it.toEducation() }
    }

    /** DTO 목록을 UI용 [Experience] 목록으로 변환 */
    fun experiencesForUi(data: MypageResponse?): List<Experience> {
        if (data == null) return emptyList()
        return data.workExperiences.map { it.toExperience() }
    }

    /** DTO 목록을 UI용 [Certification] 목록으로 변환 */
    fun certificationsForUi(data: MypageResponse?): List<Certification> {
        if (data == null) return emptyList()
        return data.certifications.map { it.toCertification() }
    }

    /** DTO 목록을 UI용 [Award] 목록으로 변환 */
    fun awardsForUi(data: MypageResponse?): List<Award> {
        if (data == null) return emptyList()
        return data.awards.map { it.toAward() }
    }
}

private fun EducationDto.toEducation(): Education = Education(
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

private fun CertificationDto.toCertification(): Certification = Certification(
    name = name,
    issuer = issuingOrganization,
    date = obtainedDate,
    score = grade.takeIf { it.isNotBlank() } ?: score.takeIf { it > 0 }?.toString()
)

private fun AwardDto.toAward(): Award = Award(
    title = awardTitle,
    content = competitionName,
    year = awardYear.toString(),
    issuer = awardingOrganization
)

private fun MypageResponse.toUpdateRequest(): MypageUpdateRequest = MypageUpdateRequest(
    educations = educations.map { e ->
        com.resumaker.app.data.remote.dto.mypage.EducationRequest(
            university = e.university,
            major = e.major,
            enrollmentYear = e.enrollmentYear,
            graduationYear = e.graduationYear,
            gpa = e.gpa
        )
    },
    awards = awards.map { a ->
        com.resumaker.app.data.remote.dto.mypage.AwardRequest(
            competitionName = a.competitionName,
            awardTitle = a.awardTitle,
            awardYear = a.awardYear,
            awardingOrganization = a.awardingOrganization
        )
    },
    certifications = certifications.map { c ->
        com.resumaker.app.data.remote.dto.mypage.CertificationRequest(
            name = c.name,
            obtainedDate = c.obtainedDate,
            issuingOrganization = c.issuingOrganization,
            score = c.score,
            grade = c.grade
        )
    },
    workExperiences = workExperiences.map { w ->
        com.resumaker.app.data.remote.dto.mypage.WorkExperienceRequest(
            companyName = w.companyName,
            startYear = w.startYear,
            endYear = w.endYear,
            jobTitle = w.jobTitle,
            jobDescription = w.jobDescription
        )
    }
)
