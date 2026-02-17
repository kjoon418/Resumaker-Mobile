package com.resumaker.feature.careermanager.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.resumaker.domain.common.ApiResult
import com.resumaker.domain.persona.GetPersonasUseCase
import com.resumaker.feature.careermanager.contract.CareerManagerIntent
import com.resumaker.feature.careermanager.contract.CareerManagerState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * 커리어 매니저(홈) 화면의 MVI ViewModel.
 * 단일 State + Intent 흐름으로 상태를 관리합니다.
 */
class CareerManagerViewModel(
    private val getPersonasUseCase: GetPersonasUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(CareerManagerState())
    val state: StateFlow<CareerManagerState> = _state.asStateFlow()

    init {
        onIntent(CareerManagerIntent.LoadPersonas)
    }

    fun onIntent(intent: CareerManagerIntent) {
        when (intent) {
            is CareerManagerIntent.LoadPersonas -> loadPersonas()
        }
    }

    fun loadPersonas() {
        viewModelScope.launch {
            _state.update { it.copy(isLoadingPersonas = true, personasError = null) }
            when (val result = getPersonasUseCase()) {
                is ApiResult.Success -> _state.update { it.copy(personas = result.data, isLoadingPersonas = false) }
                is ApiResult.Error -> _state.update { it.copy(personasError = result.message, isLoadingPersonas = false) }
                is ApiResult.NetworkError -> _state.update { it.copy(personasError = "네트워크 연결을 확인해 주세요.", isLoadingPersonas = false) }
            }
        }
    }
}
