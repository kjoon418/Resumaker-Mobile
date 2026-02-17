package com.resumaker.feature.signup.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.resumaker.domain.auth.AuthRepository
import com.resumaker.domain.auth.RegisterParams
import com.resumaker.domain.common.ApiResult
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

private const val STEP_1 = 1
private const val STEP_2 = 2

private fun genderToApi(genderDisplay: String): String = when (genderDisplay) {
    "남성" -> "M"
    "여성" -> "F"
    "기타" -> "O"
    else -> "O"
}

class SignUpViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _currentStep = MutableStateFlow(STEP_1)
    val currentStep: StateFlow<Int> = _currentStep.asStateFlow()

    private val _name = MutableStateFlow("")
    val name: StateFlow<String> = _name.asStateFlow()

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password.asStateFlow()

    private val _isTermAgreed = MutableStateFlow(false)
    val isTermAgreed: StateFlow<Boolean> = _isTermAgreed.asStateFlow()

    private val _age = MutableStateFlow("")
    val age: StateFlow<String> = _age.asStateFlow()

    private val _gender = MutableStateFlow("선택해주세요")
    val gender: StateFlow<String> = _gender.asStateFlow()

    private val _occupation = MutableStateFlow("")
    val occupation: StateFlow<String> = _occupation.asStateFlow()

    private val _contact = MutableStateFlow("")
    val contact: StateFlow<String> = _contact.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    private val _isRegistering = MutableStateFlow(false)
    val isRegistering: StateFlow<Boolean> = _isRegistering.asStateFlow()

    private val _signUpSuccessEvent = MutableSharedFlow<Unit>()
    val signUpSuccessEvent = _signUpSuccessEvent.asSharedFlow()

    fun updateName(value: String) {
        _name.update { value }
        _errorMessage.update { null }
    }

    fun updateEmail(value: String) {
        _email.update { value }
        _errorMessage.update { null }
    }

    fun updatePassword(value: String) {
        _password.update { value }
        _errorMessage.update { null }
    }

    fun updateTermAgreed(value: Boolean) {
        _isTermAgreed.update { value }
    }

    fun updateAge(value: String) {
        if (value.isEmpty() || value.all { c -> c.isDigit() }) {
            _age.update { value }
            _errorMessage.update { null }
        }
    }

    fun updateGender(value: String) {
        _gender.update { value }
        _errorMessage.update { null }
    }

    fun updateOccupation(value: String) {
        _occupation.update { value }
        _errorMessage.update { null }
    }

    fun updateContact(value: String) {
        _contact.update { value }
        _errorMessage.update { null }
    }

    fun goToNextStep() {
        _currentStep.update { STEP_2 }
    }

    fun goToPrevStep() {
        _currentStep.update { STEP_1 }
        _errorMessage.update { null }
    }

    fun register() {
        viewModelScope.launch {
            _errorMessage.update { null }
            _isRegistering.update { true }
            val params = RegisterParams(
                username = _email.value.trim(),
                email = _email.value.trim(),
                password = _password.value,
                name = _name.value.trim(),
                age = _age.value.toIntOrNull() ?: 0,
                gender = genderToApi(_gender.value),
                job = _occupation.value.trim(),
                phoneNumber = _contact.value.trim()
            )
            when (val result = authRepository.register(params)) {
                is ApiResult.Success -> _signUpSuccessEvent.emit(Unit)
                is ApiResult.Error -> _errorMessage.update { result.message }
                is ApiResult.NetworkError -> _errorMessage.update { "네트워크 연결을 확인해 주세요." }
            }
            _isRegistering.update { false }
        }
    }

    fun clearError() {
        _errorMessage.update { null }
    }
}
