package com.resumaker.app.ui.resumelist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.resumaker.app.component.card.ResumeDetailCard
import com.resumaker.app.model.Resume
import com.resumaker.app.ui.theme.ResumakerTheme

private val ScreenBackground = Color(0xFFF8FAFC)
private val FabColor = Color(0xFF6366F1)
private val FilterTextColor = Color.Gray

@Composable
fun ResumeListTopBar(
    onBackClick: () -> Unit,
    onSearchClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onBackClick) {
            Icon(
                Icons.Default.ArrowBackIosNew,
                contentDescription = "뒤로가기",
                modifier = Modifier.size(20.dp)
            )
        }
        Text(
            text = "내 이력서 목록",
            modifier = Modifier.weight(1f),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        IconButton(onClick = onSearchClick) {
            Icon(Icons.Default.Search, contentDescription = "검색")
        }
    }
}

@Composable
fun ResumeListScreen(
    resumeList: List<Resume>,
    onBackClick: () -> Unit,
    onSearchClick: () -> Unit,
    onEditClick: (Resume) -> Unit,
    onPdfDownloadClick: (Resume) -> Unit,
    onRenameClick: (Resume) -> Unit,
    onDeleteClick: (Resume) -> Unit,
    onAddNewClick: () -> Unit
) {
    Scaffold(
        topBar = {
            ResumeListTopBar(onBackClick = onBackClick, onSearchClick = onSearchClick)
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddNewClick,
                containerColor = FabColor,
                shape = CircleShape,
                modifier = Modifier.padding(bottom = 16.dp, end = 8.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "추가", tint = Color.White)
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(ScreenBackground)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "최근 수정순",
                    fontSize = 13.sp,
                    color = FilterTextColor
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Default.Tune,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = FilterTextColor
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "필터",
                        fontSize = 13.sp,
                        color = FilterTextColor
                    )
                }
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(start = 20.dp, top = 0.dp, end = 20.dp, bottom = 80.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(resumeList) { resume ->
                    ResumeDetailCard(
                        resume = resume,
                        onEdit = { onEditClick(resume) },
                        onPdfDownload = { onPdfDownloadClick(resume) },
                        onRenameClick = { onRenameClick(resume) },
                        onDeleteClick = { onDeleteClick(resume) }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ResumeListScreenPreview() {
    ResumakerTheme {
        ResumeListScreen(
            resumeList = listOf(
                Resume("1", "프론트엔드 개발자 이력서", "2025.02.05", "doc"),
                Resume("2", "이름이짱긴이력서예시를한번만들어보겠습니다어디한번이것도줄여보시지", "2025.02.01", "code")
            ),
            onBackClick = { },
            onSearchClick = { },
            onEditClick = { },
            onPdfDownloadClick = { },
            onRenameClick = { },
            onDeleteClick = { },
            onAddNewClick = { }
        )
    }
}
