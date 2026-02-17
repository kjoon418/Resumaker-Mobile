package com.resumaker.domain.auth

import com.resumaker.domain.common.ApiResult

/**
 * 로그아웃 UseCase.
 */
class LogoutUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): ApiResult<LogoutResult> =
        authRepository.logout()
}
