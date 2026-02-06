package com.resumaker.app.ui.interview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.resumaker.app.component.appbar.InterviewTopBar
import com.resumaker.app.component.chat.AiMessageBubble
import com.resumaker.app.component.chat.UserMessageBubble
import com.resumaker.app.component.input.InterviewBottomSection
import com.resumaker.app.component.progress.AnalyzingIndicator
import com.resumaker.app.component.section.InterviewerProfileHeader
import com.resumaker.app.model.ChatMessage
import com.resumaker.app.ui.theme.ResumakerTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private val BackgroundLight = Color(0xFFF8F9FA)

/** 처음 화면에만 보이는 면접관 인사 메시지 */
private val initialGreeting = ChatMessage(
    id = "ai-greeting",
    content = "안녕하세요, 지원자님. Resumaker 가상 면접에 오신 것을 환영합니다. 먼저 간단하게 자기소개 부탁드립니다.",
    time = "오전 10:00",
    isFromAi = true
)

/** 사용자 대답 후 면접관이 이어가는 대화 (순서대로 사용, 마지막 이후는 반복) */
private val followUpTemplates = listOf(
    "감사합니다. 그 경험에서 가장 어려웠던 점은 무엇이었나요?",
    "이해했습니다. 그다음으로, 팀에서 의견이 엇갈렸을 때 어떻게 조율하셨나요?",
    "좋은 답변이에요. 지원하신 포지션에서 가장 기대하는 부분은 무엇인가요?",
    "그렇군요. 최근에 새로 배우거나 도전한 기술이 있다면 소개해 주실 수 있을까요?",
    "흥미롭네요. 5년 후에는 어떤 개발자로 성장해 있고 싶으신가요?"
)

@Composable
fun InterviewScreen(
    onBackClick: () -> Unit,
    onExit: () -> Unit
) {
    var inputText by remember { mutableStateOf("") }
    val messages = remember { mutableStateListOf(initialGreeting) }
    var isAiThinking by remember { mutableStateOf(false) }
    val scrollState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    fun sendMessage() {
        val text = inputText.trim()
        if (text.isEmpty()) return
        val userMsg = ChatMessage(
            id = "user-${System.currentTimeMillis()}",
            content = text,
            time = "오전 10:00", // TODO: 실제 시간 포맷
            isFromAi = false
        )
        messages.add(userMsg)
        inputText = ""
        scope.launch {
            scrollState.animateScrollToItem(messages.size - 1)
        }

        isAiThinking = true
        scope.launch {
            delay(1200L)
            isAiThinking = false
            val turnIndex = (messages.count { it.isFromAi } - 1).coerceAtLeast(0)
            val followUp = followUpTemplates[turnIndex % followUpTemplates.size]
            val aiReply = ChatMessage(
                id = "ai-${System.currentTimeMillis()}",
                content = followUp,
                time = "오전 10:00",
                isFromAi = true
            )
            messages.add(aiReply)
            scrollState.animateScrollToItem(messages.size - 1)
        }
    }

    Scaffold(
        topBar = {
            InterviewTopBar(
                onBackClick = onBackClick,
                onExit = onExit
            )
        },
        bottomBar = {
            InterviewBottomSection(
                value = inputText,
                onValueChange = { if (it.length <= 1000) inputText = it },
                onSend = { sendMessage() }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(BackgroundLight)
        ) {
            InterviewerProfileHeader()

            LazyColumn(
                state = scrollState,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(vertical = 20.dp)
            ) {
                messages.forEach { msg ->
                    item(key = msg.id) {
                        when {
                            msg.isAnalyzing -> AnalyzingIndicator()
                            msg.isFromAi -> AiMessageBubble(msg.content, msg.time)
                            else -> UserMessageBubble(msg.content, msg.time)
                        }
                    }
                }
                if (isAiThinking) {
                    item(key = "ai-thinking") {
                        AnalyzingIndicator()
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun InterviewScreenPreview() {
    ResumakerTheme {
        InterviewScreen(
            onBackClick = { },
            onExit = { }
        )
    }
}
