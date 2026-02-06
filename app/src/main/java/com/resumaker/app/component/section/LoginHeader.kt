package com.resumaker.app.component.section

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val TopBarBlue = Color(0xFF2161EE)

@Composable
fun LoginHeader() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Resumaker",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = TopBarBlue
        )

        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text = "반가워요!\n다시 시작해볼까요?",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            lineHeight = 38.sp,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = "AI와 함께 당신의 커리어를 완성하세요.",
            fontSize = 15.sp,
            color = Color.Gray
        )
    }
}
