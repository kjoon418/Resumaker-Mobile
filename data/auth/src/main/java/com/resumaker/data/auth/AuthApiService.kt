package com.resumaker.data.auth

import com.resumaker.data.auth.dto.LoginRequest
import com.resumaker.data.auth.dto.LoginResponse
import com.resumaker.data.auth.dto.LogoutResponse
import com.resumaker.data.auth.dto.RegisterRequest
import com.resumaker.data.auth.dto.RegisterResponse
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * 인증 관련 API 정의.
 */
interface AuthApiService {

    @POST("api/users/login/")
    suspend fun login(@Body body: LoginRequest): LoginResponse

    @POST("api/users/logout/")
    suspend fun logout(): LogoutResponse

    @POST("api/users/register/")
    suspend fun register(@Body body: RegisterRequest): RegisterResponse
}
