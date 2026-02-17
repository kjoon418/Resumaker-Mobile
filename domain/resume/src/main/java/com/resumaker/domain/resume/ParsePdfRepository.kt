package com.resumaker.domain.resume

import com.resumaker.domain.common.ApiResult
import com.resumaker.domain.resume.model.ParsedResumeDetail
import java.io.File

/**
 * PDF 파싱 Repository 인터페이스.
 */
interface ParsePdfRepository {
    fun getLastParsedDetail(): ParsedResumeDetail?
    fun setLastParsedDetail(detail: ParsedResumeDetail)
    fun clearLastParsedDetail()
    suspend fun parsePdf(file: File): ApiResult<ParsedResumeDetail>
}
