package com.resumaker.domain.mypage.model

import com.resumaker.domain.common.model.Award
import com.resumaker.domain.common.model.Certification
import com.resumaker.domain.common.model.Education
import com.resumaker.domain.common.model.Experience

/**
 * 마이페이지 도메인 모델.
 */
data class MypageData(
    val educations: List<Education>,
    val awards: List<Award>,
    val certifications: List<Certification>,
    val workExperiences: List<Experience>
)
