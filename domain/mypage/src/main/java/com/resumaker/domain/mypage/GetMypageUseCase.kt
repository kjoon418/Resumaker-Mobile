package com.resumaker.domain.mypage

import com.resumaker.domain.common.ApiResult
import com.resumaker.domain.mypage.model.MypageData

class GetMypageUseCase(
    private val mypageRepository: MypageRepository
) {
    suspend operator fun invoke(): ApiResult<MypageData> = mypageRepository.getMypage()
}
