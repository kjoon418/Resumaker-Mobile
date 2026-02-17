@file:JvmName("ModelReexports")
package com.resumaker.app.model

// Re-export for backward compatibility - existing imports of com.resumaker.app.model.* continue to work
typealias Resume = com.resumaker.core.common.model.Resume
typealias Persona = com.resumaker.domain.persona.model.Persona
typealias UserProfile = com.resumaker.domain.common.model.UserProfile
typealias Education = com.resumaker.domain.common.model.Education
typealias Experience = com.resumaker.domain.common.model.Experience
typealias Certification = com.resumaker.domain.common.model.Certification
typealias Award = com.resumaker.domain.common.model.Award
typealias ParsedResumeDetail = com.resumaker.domain.resume.model.ParsedResumeDetail
typealias ProjectHistoryItem = com.resumaker.domain.resume.model.ProjectHistoryItem
typealias ExtraInfoItem = com.resumaker.core.common.model.ExtraInfoItem
typealias IconType = com.resumaker.core.designsystem.IconType
