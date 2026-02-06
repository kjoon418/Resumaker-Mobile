package com.resumaker.app.ui.careermanager

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.resumaker.app.data.persona.PersonaRepository
import com.resumaker.app.data.remote.ApiResult
import com.resumaker.app.model.Persona
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * 커리어 매니저(홈) 화면의 상태 및 로직.
 * Repository는 ViewModel만 의존하므로, Screen은 ViewModel만 구독합니다.
 */
class CareerManagerViewModel(
    private val personaRepository: PersonaRepository
) : ViewModel() {

    private val _personas = MutableStateFlow<List<Persona>>(emptyList())
    val personas: StateFlow<List<Persona>> = _personas.asStateFlow()

    private val _isLoadingPersonas = MutableStateFlow(false)
    val isLoadingPersonas: StateFlow<Boolean> = _isLoadingPersonas.asStateFlow()

    private val _personasError = MutableStateFlow<String?>(null)
    val personasError: StateFlow<String?> = _personasError.asStateFlow()

    init {
        loadPersonas()
    }

    fun loadPersonas() {
        viewModelScope.launch {
            _isLoadingPersonas.update { true }
            _personasError.update { null }
            when (val result = personaRepository.getPersonas()) {
                is ApiResult.Success -> _personas.update { result.data }
                is ApiResult.Error -> _personasError.update { result.message }
                is ApiResult.NetworkError -> _personasError.update { "네트워크 연결을 확인해 주세요." }
            }
            _isLoadingPersonas.update { false }
        }
    }
}
