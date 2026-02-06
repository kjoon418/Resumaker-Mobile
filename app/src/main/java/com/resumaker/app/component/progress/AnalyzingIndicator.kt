package com.resumaker.app.component.progress

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.resumaker.app.ui.theme.ResumakerTheme

/**
 * AI가 답변을 분석 중일 때 표시하는 로딩 인디케이터.
 */
@Composable
fun AnalyzingIndicator() {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text("● ● ● ", color = Color.LightGray)
        Text("답변 분석 중...", fontSize = 12.sp, color = Color.Gray)
    }
}

@Preview(showBackground = true)
@Composable
private fun AnalyzingIndicatorPreview() {
    ResumakerTheme {
        AnalyzingIndicator()
    }
}
