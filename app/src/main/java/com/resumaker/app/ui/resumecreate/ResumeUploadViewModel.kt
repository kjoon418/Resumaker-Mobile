package com.resumaker.app.ui.resumecreate

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.resumaker.app.data.parsepdf.ParsePdfRepository
import com.resumaker.app.data.remote.ApiResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File

/**
 * 기존 이력서 제출(업로드) 화면 ViewModel.
 * - 선택된 파일 보관, PDF 업로드(파싱) 호출, 로딩/에러 상태 제공.
 * - 파싱 성공 시 [ParsePdfRepository]에 결과를 저장한 뒤 다음 단계로 이동하도록 콜백 사용.
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

    /** 선택된 파일을 설정합니다. (파일 선택기 결과) */
    fun setUploadedFile(file: File?) {
        _uploadedFile.value = file
        _uploadError.value = null
    }

    /** 업로드된 파일을 제거합니다. */
    fun removeUploadedFile() {
        _uploadedFile.value = null
        _uploadError.value = null
    }

    /**
     * 현재 선택된 PDF를 서버에 업로드하여 파싱합니다.
     * 성공 시 repository에 결과를 저장하고 [onParseSuccess]를 호출합니다.
     * 파일이 없으면 [onParseSuccess]만 호출하지 않고 [onNoFile]을 호출할 수 있도록 함.
     */
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
                is ApiResult.Success -> {
                    onParseSuccess()
                }
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
