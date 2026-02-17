package com.resumaker.domain.mypage

import com.resumaker.domain.common.ApiResult
import com.resumaker.domain.mypage.model.MypageData
import com.resumaker.domain.mypage.model.MypageUpdateParams

class UpdateMypageUseCase(
    private val mypageRepository: MypageRepository
) {
    suspend operator fun invoke(params: MypageUpdateParams): ApiResult<MypageData> =
        mypageRepository.updateMypage(params)
}
