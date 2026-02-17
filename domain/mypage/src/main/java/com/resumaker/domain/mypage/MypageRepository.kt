package com.resumaker.domain.mypage

import com.resumaker.domain.common.ApiResult
import com.resumaker.domain.mypage.model.MypageData
import com.resumaker.domain.mypage.model.MypageUpdateParams

/**
 * 마이페이지 Repository 인터페이스.
 */
interface MypageRepository {
    suspend fun getMypage(): ApiResult<MypageData>
    suspend fun updateMypage(params: MypageUpdateParams): ApiResult<MypageData>
}
