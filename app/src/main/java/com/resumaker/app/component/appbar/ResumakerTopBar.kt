package com.resumaker.app.component.appbar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * 뒤로가기 버튼과 중앙 타이틀이 있는 재사용 가능한 상단 바.
 *
 * @param onBackClick 뒤로가기 클릭 시 호출. [showBackButton]이 false면 사용되지 않음.
 * @param title 상단 바에 표시할 타이틀.
 * @param showBackButton true면 좌측 뒤로가기 버튼 표시, false면 숨김.
 */
@Composable
fun ResumakerTopBar(
    onBackClick: () -> Unit,
    title: String = "Resumaker",
    showBackButton: Boolean = true,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
    ) {
        if (showBackButton) {
            IconButton(
                onClick = onBackClick,
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Icon(
                    Icons.Default.ArrowBackIosNew,
                    contentDescription = "뒤로가기",
                    modifier = Modifier.size(20.dp)
                )
            }
        }
        Text(
            text = title,
            modifier = Modifier.align(Alignment.Center),
            color = Color(0xFF2161EE),
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ResumakerTopBarPreview() {
    ResumakerTopBar(onBackClick = { })
}
