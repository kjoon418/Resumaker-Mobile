package com.resumaker.feature.mypage.component.item

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.resumaker.domain.common.model.Award

private val BodyGray = Color(0xFF64748B)
private val DarkGray = Color(0xFF475569)

@Composable
fun AwardItem(
    award: Award,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(vertical = 12.dp)) {
        Text(
            text = award.title,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurface
        )
        Text(
            text = "${award.issuer} · ${award.year}",
            fontSize = 13.sp,
            color = BodyGray
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = award.content,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            color = DarkGray
        )
    }
}
