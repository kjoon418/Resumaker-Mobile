package com.resumaker.app.data.parsepdf

import com.resumaker.app.data.remote.dto.ParsePdfResponse
import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

/**
 * 이력서 PDF 파싱 API.
 * POST /parse-pdf/ — multipart/form-data로 PDF 업로드 후 폼 자동 채우기용 JSON 반환.
 */
interface ParsePdfApiService {

    @Multipart
    @POST("parse-pdf/")
    suspend fun parsePdf(@Part file: MultipartBody.Part): ParsePdfResponse
}
