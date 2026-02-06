package com.resumaker.app.component.card

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.resumaker.app.ui.theme.ResumakerTheme

private val SectionTitleGray = Color(0xFF64748B)
private val EditIconTint = Color(0xFF9ba5b5)

/**
 * 마이페이지 섹션용 카드. 제목 + 수정/추가 버튼 + 컨텐츠.
 * @param title 섹션 제목 (예: "기본 정보", "학력 사항")
 * @param onEditClick 수정 버튼 클릭 (기본 정보 등). null이면 수정 버튼 미표시
 * @param onAddClick 추가 버튼 클릭 (학력/경력 등 리스트). null이면 추가 버튼 미표시
 */
@Composable
fun InfoSectionCard(
    title: String,
    modifier: Modifier = Modifier,
    onEditClick: (() -> Unit)? = null,
    onAddClick: (() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    SectionCard(modifier = modifier) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = SectionTitleGray
                )
                if (onEditClick != null) {
                    IconButton(
                        onClick = onEditClick,
                        modifier = Modifier.padding(start = 4.dp)
                    ) {
                        Icon(
                            Icons.Default.Edit,
                            contentDescription = "수정",
                            tint = EditIconTint,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
                if (onAddClick != null) {
                    IconButton(
                        onClick = onAddClick,
                        modifier = Modifier.padding(start = 4.dp)
                    ) {
                        Icon(
                            Icons.Default.Add,
                            contentDescription = "추가",
                            tint = EditIconTint,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
            content()
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun InfoSectionCardPreview() {
    ResumakerTheme {
        InfoSectionCard(
            title = "기본 정보",
            onEditClick = { },
            content = {
                Text("내용 예시", fontSize = 14.sp, color = Color.DarkGray)
            }
        )
    }
}
