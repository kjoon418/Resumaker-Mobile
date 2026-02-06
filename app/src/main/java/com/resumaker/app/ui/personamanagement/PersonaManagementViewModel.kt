package com.resumaker.app.ui.personamanagement

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
 * 페르소나 관리 화면의 상태 및 로직.
 * Repository는 ViewModel만 의존하므로, Screen은 ViewModel만 구독합니다.
 */
class PersonaManagementViewModel(
    private val personaRepository: PersonaRepository
) : ViewModel() {

    private val _personas = MutableStateFlow<List<Persona>>(emptyList())
    val personas: StateFlow<List<Persona>> = _personas.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    /** 수정 다이얼로그에 표시할 페르소나. null이면 다이얼로그 비표시. */
    private val _editingPersona = MutableStateFlow<Persona?>(null)
    val editingPersona: StateFlow<Persona?> = _editingPersona.asStateFlow()

    private val _isUpdating = MutableStateFlow(false)
    val isUpdating: StateFlow<Boolean> = _isUpdating.asStateFlow()

    /** 삭제 확인 다이얼로그에 표시할 페르소나. null이면 다이얼로그 비표시. */
    private val _personaToDelete = MutableStateFlow<Persona?>(null)
    val personaToDelete: StateFlow<Persona?> = _personaToDelete.asStateFlow()

    private val _isDeleting = MutableStateFlow(false)
    val isDeleting: StateFlow<Boolean> = _isDeleting.asStateFlow()

    init {
        loadPersonas()
    }

    fun startEdit(persona: Persona) {
        _editingPersona.update { persona }
        _errorMessage.update { null }
    }

    fun cancelEdit() {
        _editingPersona.update { null }
        _errorMessage.update { null }
    }

    /**
     * 페르소나 수정 API 호출. 성공 시 목록 갱신 후 다이얼로그 닫음.
     */
    fun updatePersona(
        id: Int,
        name: String,
        description: String,
        prompt: String,
        isActive: Boolean
    ) {
        viewModelScope.launch {
            _isUpdating.update { true }
            _errorMessage.update { null }
            when (val result = personaRepository.updatePersona(id, name, description, prompt, isActive)) {
                is ApiResult.Success -> {
                    _personas.update { list ->
                        list.map { if (it.id == result.data.id) result.data else it }
                    }
                    cancelEdit()
                }
                is ApiResult.Error -> _errorMessage.update { result.message }
                is ApiResult.NetworkError -> _errorMessage.update { "네트워크 연결을 확인해 주세요." }
            }
            _isUpdating.update { false }
        }
    }

    fun startDelete(persona: Persona) {
        _personaToDelete.update { persona }
        _errorMessage.update { null }
    }

    fun cancelDelete() {
        _personaToDelete.update { null }
        _errorMessage.update { null }
    }

    /**
     * 페르소나 삭제 API 호출. 성공 시 목록에서 제거 후 삭제 다이얼로그 닫음.
     */
    fun deletePersona(id: Int) {
        viewModelScope.launch {
            _isDeleting.update { true }
            _errorMessage.update { null }
            when (val result = personaRepository.deletePersona(id)) {
                is ApiResult.Success -> {
                    _personas.update { list -> list.filter { it.id != id.toString() } }
                    cancelDelete()
                }
                is ApiResult.Error -> _errorMessage.update { result.message }
                is ApiResult.NetworkError -> _errorMessage.update { "네트워크 연결을 확인해 주세요." }
            }
            _isDeleting.update { false }
        }
    }

    fun loadPersonas() {
        viewModelScope.launch {
            _isLoading.update { true }
            _errorMessage.update { null }
            when (val result = personaRepository.getPersonas()) {
                is ApiResult.Success -> _personas.update { result.data }
                is ApiResult.Error -> _errorMessage.update { result.message }
                is ApiResult.NetworkError -> _errorMessage.update { "네트워크 연결을 확인해 주세요." }
            }
            _isLoading.update { false }
        }
    }
}
