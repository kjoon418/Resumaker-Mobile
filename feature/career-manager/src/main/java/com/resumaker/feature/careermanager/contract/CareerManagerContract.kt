package com.resumaker.feature.careermanager.contract

import com.resumaker.domain.persona.model.Persona

/**
 * MVI Contract for CareerManagerScreen.
 */

/** UI 상태 - viewState.copy()로 불변성 유지 */
data class CareerManagerState(
    val personas: List<Persona> = emptyList(),
    val isLoadingPersonas: Boolean = false,
    val personasError: String? = null
)

/** Intent(Action) - UI에서 발생하는 사용자 의도 */
sealed interface CareerManagerIntent {
    data object LoadPersonas : CareerManagerIntent
}
