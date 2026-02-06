package com.resumaker.app.component.appbar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val PrimaryBlue = Color(0xFF2161EE)

/**
 * 가상 면접 화면용 상단 바.
 * 중앙에 "가상 면접 진행 중" / "Resumaker AI", 좌측 뒤로가기, 우측 "면접 종료" 버튼.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InterviewTopBar(
    onBackClick: () -> Unit,
    onExit: () -> Unit
) {
    CenterAlignedTopAppBar(
        title = {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("가상 면접 진행 중", fontSize = 12.sp, color = PrimaryBlue)
                Text("Resumaker AI", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
        },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    Icons.Default.ArrowBackIosNew,
                    contentDescription = "뒤로가기",
                    modifier = Modifier.size(20.dp)
                )
            }
        },
        actions = {
            Button(
                onClick = onExit,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFF1F1)),
                shape = RoundedCornerShape(8.dp),
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp)
            ) {
                Text("면접 종료", color = Color.Red, fontSize = 12.sp)
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.White)
    )
}

@Preview(showBackground = true)
@Composable
private fun InterviewTopBarPreview() {
    InterviewTopBar(onBackClick = { }, onExit = { })
}
