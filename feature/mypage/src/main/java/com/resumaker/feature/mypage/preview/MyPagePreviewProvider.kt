package com.resumaker.feature.mypage.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.resumaker.domain.common.model.Award
import com.resumaker.domain.common.model.Certification
import com.resumaker.domain.common.model.Education
import com.resumaker.domain.common.model.Experience
import com.resumaker.domain.common.model.UserProfile

/** MyPageScreen - Empty, Success, hasChanges 상태 */
data class MyPagePreviewData(
    val user: UserProfile,
    val educations: List<Education>,
    val experiences: List<Experience>,
    val certifications: List<Certification>,
    val awards: List<Award>,
    val hasChanges: Boolean = false
)

class MyPagePreviewProvider : PreviewParameterProvider<MyPagePreviewData> {
    override val values: Sequence<MyPagePreviewData> = sequenceOf(
        MyPagePreviewData(
            user = UserProfile("홍길동", "hong@example.com", 28, "MALE", "프론트엔드 개발자", "010-1234-5678"),
            educations = emptyList(),
            experiences = emptyList(),
            certifications = emptyList(),
            awards = emptyList()
        ),
        MyPagePreviewData(
            user = UserProfile("홍길동", "hong@example.com", 28, "MALE", "프론트엔드 개발자", "010-1234-5678"),
            educations = listOf(Education("서울대학교", "컴퓨터공학과", "2014.03 ~ 2018.02", "4.2/4.5")),
            experiences = listOf(
                Experience("(주)테크컴퍼니", "시니어 프론트엔드 개발자", "2022.01 ~ 현재", "React 기반 웹 서비스 개발 및 팀 리드.", true),
                Experience("이전 회사", "주니어 개발자", "2018.03 ~ 2021.12", "웹 프론트엔드 개발 및 유지보수.", false)
            ),
            certifications = listOf(
                Certification("정보처리기사", "한국산업인력공단", "2020.05", "1차 합격"),
                Certification("TOEIC", "ETS", "2019.03", "920")
            ),
            awards = listOf(Award("우수 사원상", "연간 성과 평가 우수", "2023", "테크컴퍼니"))
        ),
        MyPagePreviewData(
            user = UserProfile("홍길동", "hong@example.com", 28, "MALE", "프론트엔드 개발자", "010-1234-5678"),
            educations = listOf(Education("서울대학교", "컴퓨터공학과", "2014.03 ~ 2018.02", "4.2/4.5")),
            experiences = listOf(
                Experience("(주)테크컴퍼니", "시니어 프론트엔드 개발자", "2022.01 ~ 현재", "React 기반 웹 서비스 개발 및 팀 리드.", true),
                Experience("이전 회사", "주니어 개발자", "2018.03 ~ 2021.12", "웹 프론트엔드 개발 및 유지보수.", false)
            ),
            certifications = listOf(
                Certification("정보처리기사", "한국산업인력공단", "2020.05", "1차 합격"),
                Certification("TOEIC", "ETS", "2019.03", "920")
            ),
            awards = listOf(Award("우수 사원상", "연간 성과 평가 우수", "2023", "테크컴퍼니")),
            hasChanges = true
        )
    )
}
