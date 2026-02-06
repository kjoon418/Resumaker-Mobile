package com.resumaker.app.model

/**
 * 가상 면접 채팅 메시지 모델.
 *
 * @param id 리스트 구분용 고유 식별자
 * @param content 메시지 내용
 * @param time 표시 시간 (예: "오전 10:02")
 * @param isFromAi true면 AI 면접관, false면 사용자
 * @param isAnalyzing AI가 답변 분석 중일 때 true (로딩 인디케이터 표시용)
 */
data class ChatMessage(
    val id: String,
    val content: String,
    val time: String,
    val isFromAi: Boolean,
    val isAnalyzing: Boolean = false
)
