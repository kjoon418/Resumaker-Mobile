package com.resumaker.domain.resume

import com.resumaker.domain.resume.model.ParsedResumeDetail

class GetLastParsedDetailUseCase(
    private val parsePdfRepository: ParsePdfRepository
) {
    operator fun invoke(): ParsedResumeDetail? {
        val detail = parsePdfRepository.getLastParsedDetail()
        if (detail != null) {
            parsePdfRepository.clearLastParsedDetail()
        }
        return detail
    }
}
