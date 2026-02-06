package com.resumaker.app.component.section

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.resumaker.app.ui.theme.ResumakerTheme

/**
 * 섹션 제목 + 선택적 부제목 + 전체보기 버튼.
 * 목록 화면 등에서 재사용 가능한 순수 컴포넌트.
 */
@Composable
fun SectionHeader(
    title: String,
    modifier: Modifier = Modifier,
    subtitle: String? = null,
    onSeeAllClick: () -> Unit
) {
    Column(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
            TextButton(onClick = onSeeAllClick) {
                Text(
                    text = "전체보기",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 14.sp
                )
            }
        }
        if (subtitle != null) {
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 13.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SectionHeaderPreview() {
    ResumakerTheme {
        SectionHeader(
            title = "내 이력서",
            onSeeAllClick = { }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SectionHeaderWithSubtitlePreview() {
    ResumakerTheme {
        SectionHeader(
            title = "면접관 페르소나",
            subtitle = "첨삭 및 모의 면접에 활용되는 성향입니다.",
            onSeeAllClick = { }
        )
    }
}
