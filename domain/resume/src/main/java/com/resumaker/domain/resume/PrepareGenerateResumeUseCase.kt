package com.resumaker.domain.resume

import com.resumaker.domain.resume.model.ProjectHistoryItem
import com.resumaker.domain.resume.model.GenerateResumeParams
import com.resumaker.domain.resume.model.GenerateResumeProjectItem

class PrepareGenerateResumeUseCase(
    private val generateResumeRepository: GenerateResumeRepository
) {
    operator fun invoke(
        resumeFormat: String,
        targetRole: String,
        slogan: String,
        strengthKeywords: List<String>,
        projectHistoryItems: List<ProjectHistoryItem>,
        collaborationStyle: String,
        techStacks: List<String>,
        futureGoals: String
    ) {
        val params = GenerateResumeParams(
            resumeFormat = resumeFormat.takeIf { it.isNotBlank() },
            targetRole = targetRole.takeIf { it.isNotBlank() },
            headline = slogan.takeIf { it.isNotBlank() },
            strengthKeywords = strengthKeywords.takeIf { it.isNotEmpty() },
            projects = projectHistoryItems.map { item ->
                GenerateResumeProjectItem(
                    title = item.projectName.takeIf { it.isNotBlank() },
                    bullets = item.keyTasks.split("\n").map { it.trim() }.filter { it.isNotBlank() }.takeIf { it.isNotEmpty() }
                )
            }.takeIf { it.isNotEmpty() },
            collaborationStyle = collaborationStyle.takeIf { it.isNotBlank() },
            mainTechStack = techStacks.takeIf { it.isNotEmpty() },
            futureGoal = futureGoals.takeIf { it.isNotBlank() }
        )
        generateResumeRepository.setPendingRequest(params)
    }
}
