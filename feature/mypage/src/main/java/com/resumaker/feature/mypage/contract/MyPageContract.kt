package com.resumaker.feature.mypage.contract

import com.resumaker.domain.common.model.Award
import com.resumaker.domain.common.model.Certification
import com.resumaker.domain.common.model.Education
import com.resumaker.domain.common.model.Experience
import com.resumaker.domain.mypage.model.MypageData

/** UI 상태 - viewState.copy()로 불변성 유지 */
data class MyPageState(
    val mypageData: MypageData? = null,
    val isLoading: Boolean = false,
    val isSaving: Boolean = false,
    val hasChanges: Boolean = false,
    val errorMessage: String? = null
) {
    val educations: List<Education> get() = mypageData?.educations.orEmpty()
    val experiences: List<Experience> get() = mypageData?.workExperiences.orEmpty()
    val certifications: List<Certification> get() = mypageData?.certifications.orEmpty()
    val awards: List<Award> get() = mypageData?.awards.orEmpty()
}

/** Intent(Action) - UI에서 발생하는 사용자 의도 */
sealed interface MyPageIntent {
    data object LoadMypage : MyPageIntent
    data object SaveMypage : MyPageIntent
    data object ClearErrorMessage : MyPageIntent
}
