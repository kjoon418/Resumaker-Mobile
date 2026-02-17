package com.resumaker.domain.resume

import com.resumaker.domain.resume.model.GeneratedResume

class GetGeneratedResumeUseCase(
    private val generateResumeRepository: GenerateResumeRepository
) {
    operator fun invoke(): GeneratedResume? = generateResumeRepository.getGeneratedResume()
}
