package com.resumaker.app.component.card

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.resumaker.app.data.remote.dto.GeneratedResumeItem

private val PreviewCardBackground = Color(0xFF0F172A)
private val SectionAccent = Color(0xFF6366F1)

/**
 * [ResumeCompletionScreen]과 [ResumeEditScreen]에서 동일하게 사용하는
 * 생성 이력서 목 데이터 표시 카드.
 */
@Composable
fun GeneratedResumeContentCard(
    items: List<GeneratedResumeItem>,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = PreviewCardBackground),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(24.dp)) {
            items.forEachIndexed { index, item ->
                if (index > 0) {
                    Spacer(modifier = Modifier.height(16.dp))
                    HorizontalDivider(color = Color.White.copy(alpha = 0.1f))
                    Spacer(modifier = Modifier.height(16.dp))
                }
                when (item.type) {
                    "SIMPLE" -> {
                        Text(
                            text = item.content.orEmpty(),
                            color = Color.White,
                            fontSize = 15.sp,
                            lineHeight = 22.sp
                        )
                    }
                    "TITLED" -> {
                        Text(
                            text = item.subTitle.orEmpty(),
                            color = SectionAccent,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = item.content.orEmpty(),
                            color = Color.White,
                            fontSize = 15.sp,
                            lineHeight = 22.sp
                        )
                    }
                    else -> {
                        Text(
                            text = item.content.orEmpty(),
                            color = Color.White,
                            fontSize = 15.sp,
                            lineHeight = 22.sp
                        )
                    }
                }
            }
        }
    }
}

/**
 * 생성된 이력서가 없을 때 표시하는 플레이스홀더 카드.
 */
@Composable
fun PlaceholderResumeCard(
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = PreviewCardBackground),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Text(
            text = "이력서 내용이 없습니다.",
            modifier = Modifier.padding(24.dp),
            color = Color.White.copy(alpha = 0.7f),
            fontSize = 15.sp
        )
    }
}
