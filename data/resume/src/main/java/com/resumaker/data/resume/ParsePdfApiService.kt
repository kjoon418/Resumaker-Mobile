package com.resumaker.data.resume

import com.resumaker.data.resume.dto.ParsePdfResponse
import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ParsePdfApiService {

    @Multipart
    @POST("parse-pdf/")
    suspend fun parsePdf(@Part file: MultipartBody.Part): ParsePdfResponse
}
