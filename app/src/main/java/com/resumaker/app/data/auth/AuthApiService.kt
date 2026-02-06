package com.resumaker.app.data.auth

import com.resumaker.app.data.remote.dto.LoginRequest
import com.resumaker.app.data.remote.dto.LoginResponse
import com.resumaker.app.data.remote.dto.LogoutResponse
import com.resumaker.app.data.remote.dto.RegisterRequest
import com.resumaker.app.data.remote.dto.RegisterResponse
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * 인증 관련 API 정의.
 */
interface AuthApiService {

    /**
     * 로그인.
     * POST /api/users/login/
     * 작업 ID: users_login_create
     * 성공 시 200 + 세션 쿠키 설정. 이후 요청에 쿠키 자동 포함.
     */
    @POST("api/users/login/")
    suspend fun login(@Body body: LoginRequest): LoginResponse

    /**
     * 로그아웃.
     * POST /api/users/logout/
     * 작업 ID: users_logout_create
     * 현재 세션을 종료하고 쿠키를 무효화합니다.
     */
    @POST("api/users/logout/")
    suspend fun logout(): LogoutResponse

    /**
     * 회원가입.
     * POST /api/users/register/
     * 작업 ID: users_register_create
     * 성공 시 201 Created, 생성된 사용자 정보 반환.
     */
    @POST("api/users/register/")
    suspend fun register(@Body body: RegisterRequest): RegisterResponse
}
