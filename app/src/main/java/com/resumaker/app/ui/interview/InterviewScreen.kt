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
import kotlinx.coroutines.launch

private val BackgroundLight = Color(0xFFF8F9FA)

@Composable
fun InterviewScreen(
    onBackClick: () -> Unit,
    onExit: () -> Unit
) {
    var inputText by remember { mutableStateOf("") }
    val messages = remember {
        mutableStateListOf(
            ChatMessage(
                id = "1",
                content = "안녕하세요, 지원자님. Resumaker 가상 면접에 오신 것을 환영합니다. 먼저 간단하게 자기소개 부탁드립니다.",
                time = "오전 10:02",
                isFromAi = true
            ),
            ChatMessage(
                id = "2",
                content = "안녕하세요. 저는 3년 차 프론트엔드 개발자로서...",
                time = "오전 10:03",
                isFromAi = false
            ),
            ChatMessage(
                id = "3",
                content = "AI 기술을 활용한 경험이 인상적이네요. 해당 프로젝트에서...",
                time = "오전 10:04",
                isFromAi = true
            )
        )
    }
    val scrollState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    fun sendMessage() {
        val text = inputText.trim()
        if (text.isEmpty()) return
        messages.add(
            ChatMessage(
                id = "user-${System.currentTimeMillis()}",
                content = text,
                time = "오전 10:05", // TODO: 실제 시간 포맷
                isFromAi = false
            )
        )
        inputText = ""
        scope.launch {
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
