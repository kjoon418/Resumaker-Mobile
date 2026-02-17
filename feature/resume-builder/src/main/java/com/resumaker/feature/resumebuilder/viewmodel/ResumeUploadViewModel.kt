package com.resumaker.feature.resumebuilder.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.resumaker.domain.resume.ParsePdfRepository
import com.resumaker.domain.common.ApiResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File

/**
 * 기존 이력서 제출(업로드) 화면 ViewModel.
 */
class ResumeUploadViewModel(
    private val parsePdfRepository: ParsePdfRepository
) : ViewModel() {

    private val _uploadedFile = MutableStateFlow<File?>(null)
    val uploadedFile: StateFlow<File?> = _uploadedFile.asStateFlow()

    private val _isUploading = MutableStateFlow(false)
    val isUploading: StateFlow<Boolean> = _isUploading.asStateFlow()

    private val _uploadError = MutableStateFlow<String?>(null)
    val uploadError: StateFlow<String?> = _uploadError.asStateFlow()

    fun setUploadedFile(file: File?) {
        _uploadedFile.value = file
        _uploadError.value = null
    }

    fun removeUploadedFile() {
        _uploadedFile.value = null
        _uploadError.value = null
    }

    fun submitPdfAndNavigate(
        onParseSuccess: () -> Unit,
        onNoFile: () -> Unit
    ) {
        val file = _uploadedFile.value
        if (file == null) {
            onNoFile()
            return
        }
        viewModelScope.launch {
            _isUploading.update { true }
            _uploadError.update { null }
            when (val result = parsePdfRepository.parsePdf(file)) {
                is ApiResult.Success -> onParseSuccess()
                is ApiResult.Error -> _uploadError.update { result.message }
                is ApiResult.NetworkError -> _uploadError.update { "네트워크 연결을 확인해 주세요." }
            }
            _isUploading.update { false }
        }
    }

    fun clearError() {
        _uploadError.value = null
    }
}
