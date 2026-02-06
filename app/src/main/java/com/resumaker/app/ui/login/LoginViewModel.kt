package com.resumaker.app.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.resumaker.app.data.auth.AuthRepository
import com.resumaker.app.data.remote.ApiResult
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * 로그인 화면의 UI 상태 및 로직을 담당하는 ViewModel.
 * Screen은 ViewModel의 상태만 구독하고, 사용자 입력/액션은 ViewModel에 위임합니다.
 */
class LoginViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    /** 로그인 성공 시 한 번만 전달하는 이벤트. Screen에서 수신 후 onLoginSuccess 콜백 호출. */
    private val _loginSuccessEvent = MutableSharedFlow<Unit>()
    val loginSuccessEvent = _loginSuccessEvent.asSharedFlow()

    fun updateEmail(value: String) {
        _email.update { value }
        _errorMessage.update { null }
    }

    fun updatePassword(value: String) {
        _password.update { value }
        _errorMessage.update { null }
    }

    fun login() {
        viewModelScope.launch {
            _errorMessage.update { null }
            _isLoading.update { true }
            when (val result = authRepository.login(_email.value, _password.value)) {
                is ApiResult.Success -> _loginSuccessEvent.emit(Unit)
                is ApiResult.Error -> _errorMessage.update { result.message }
                is ApiResult.NetworkError -> _errorMessage.update { "네트워크 연결을 확인해 주세요." }
            }
            _isLoading.update { false }
        }
    }

    fun clearError() {
        _errorMessage.update { null }
    }
}
