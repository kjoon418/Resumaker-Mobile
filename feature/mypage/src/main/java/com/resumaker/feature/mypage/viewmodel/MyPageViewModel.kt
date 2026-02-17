package com.resumaker.feature.mypage.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.resumaker.domain.common.ApiResult
import com.resumaker.domain.mypage.GetMypageUseCase
import com.resumaker.domain.mypage.UpdateMypageUseCase
import com.resumaker.domain.mypage.toUpdateParams
import com.resumaker.feature.mypage.contract.MyPageIntent
import com.resumaker.feature.mypage.contract.MyPageState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * 마이페이지 화면의 MVI ViewModel.
 * 단일 State + Intent 흐름으로 상태를 관리합니다.
 */
class MyPageViewModel(
    private val getMypageUseCase: GetMypageUseCase,
    private val updateMypageUseCase: UpdateMypageUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(MyPageState())
    val state: StateFlow<MyPageState> = _state.asStateFlow()

    init {
        onIntent(MyPageIntent.LoadMypage)
    }

    fun onIntent(intent: MyPageIntent) {
        when (intent) {
            is MyPageIntent.LoadMypage -> loadMypage()
            is MyPageIntent.SaveMypage -> saveMypage()
            is MyPageIntent.ClearErrorMessage -> _state.update { it.copy(errorMessage = null) }
        }
    }

    private fun loadMypage() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, errorMessage = null) }
            when (val result = getMypageUseCase()) {
                is ApiResult.Success -> _state.update {
                    it.copy(mypageData = result.data, isLoading = false)
                }
                is ApiResult.Error -> _state.update {
                    it.copy(errorMessage = result.message, isLoading = false)
                }
                is ApiResult.NetworkError -> _state.update {
                    it.copy(errorMessage = "네트워크 연결을 확인해 주세요.", isLoading = false)
                }
            }
        }
    }

    private fun saveMypage() {
        val current = _state.value.mypageData ?: return
        viewModelScope.launch {
            _state.update { it.copy(isSaving = true, errorMessage = null) }
            when (val result = updateMypageUseCase(current.toUpdateParams())) {
                is ApiResult.Success -> _state.update {
                    it.copy(mypageData = result.data, hasChanges = false, isSaving = false)
                }
                is ApiResult.Error -> _state.update {
                    it.copy(errorMessage = result.message, isSaving = false)
                }
                is ApiResult.NetworkError -> _state.update {
                    it.copy(errorMessage = "네트워크 연결을 확인해 주세요.", isSaving = false)
                }
            }
        }
    }
}
