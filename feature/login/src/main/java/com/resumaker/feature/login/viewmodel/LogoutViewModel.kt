package com.resumaker.feature.login.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.resumaker.domain.auth.LogoutUseCase
import com.resumaker.domain.common.ApiResult
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class LogoutViewModel(
    private val logoutUseCase: LogoutUseCase
) : ViewModel() {

    private val _effect = Channel<LogoutEffect>(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()

    fun logout() {
        viewModelScope.launch {
            when (val result = logoutUseCase()) {
                is ApiResult.Success -> _effect.send(LogoutEffect.NavigateToLogin)
                is ApiResult.Error -> _effect.send(LogoutEffect.ShowError(result.message))
                is ApiResult.NetworkError -> _effect.send(LogoutEffect.ShowError("네트워크 연결을 확인해 주세요."))
            }
        }
    }
}

sealed interface LogoutEffect {
    data object NavigateToLogin : LogoutEffect
    data class ShowError(val message: String) : LogoutEffect
}
