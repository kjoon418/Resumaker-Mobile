package com.resumaker.app.component.section

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ResumeFormatSection(
    resumeFormat: String,
    onResumeFormatChange: (String) -> Unit,
    onFormatFileSelect: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "이력서 형식",
                fontWeight = FontWeight.Bold
            )
            TextButton(onClick = onFormatFileSelect) {
                Text(
                    "파일로 전달하기",
                    fontSize = 14.sp,
                    color = ResumeDetailInputPrimaryBlue
                )
            }
        }
        OutlinedTextField(
            value = resumeFormat,
            onValueChange = onResumeFormatChange,
            placeholder = {
                Text(
                    "이력서의 양식을 설명해주세요. (예: 이름, 자기소개, 기술 스택)",
                    color = Color.LightGray,
                    fontSize = 14.sp
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 120.dp),
            shape = RoundedCornerShape(12.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = ResumeDetailInputPrimaryBlue,
                unfocusedBorderColor = Color(0xFFE0E0E0),
                cursorColor = ResumeDetailInputPrimaryBlue
            )
        )
    }
}
