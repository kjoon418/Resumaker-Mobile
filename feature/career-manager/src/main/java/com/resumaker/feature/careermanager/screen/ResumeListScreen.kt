package com.resumaker.feature.careermanager.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.resumaker.feature.careermanager.component.appbar.ResumeListTopBar
import com.resumaker.core.ui.bottombar.ResumakerBottomBar
import com.resumaker.core.ui.card.ResumeDetailCard
import com.resumaker.core.common.model.Resume
import com.resumaker.feature.careermanager.preview.ResumeListProvider
import com.resumaker.core.navigation.NavRoute

private val ScreenBackground = Color(0xFFF8FAFC)
private val FabColor = Color(0xFF6366F1)
private val FilterTextColor = Color.Gray

@Composable
fun ResumeListScreen(
    resumeList: List<Resume>,
    onBackClick: () -> Unit,
    onSearchClick: () -> Unit,
    onEditClick: (Resume) -> Unit,
    onPdfDownloadClick: (Resume) -> Unit,
    onRenameClick: (Resume) -> Unit,
    onDeleteClick: (Resume) -> Unit,
    onAddNewClick: () -> Unit,
    onNavigate: (NavRoute) -> Unit = {}
) {
    Scaffold(
        topBar = {
            ResumeListTopBar(onBackClick = onBackClick, onSearchClick = onSearchClick)
        },
        bottomBar = {
            ResumakerBottomBar(
                currentRoute = NavRoute.AllResumes,
                onNavigate = onNavigate
            )
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
                contentPadding = PaddingValues(start = 20.dp, top = 0.dp, end = 20.dp, bottom = 160.dp),
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
private fun ResumeListScreenPreview(
    @PreviewParameter(ResumeListProvider::class) resumeList: List<Resume>
) {
    MaterialTheme {
        ResumeListScreen(
            resumeList = resumeList,
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
