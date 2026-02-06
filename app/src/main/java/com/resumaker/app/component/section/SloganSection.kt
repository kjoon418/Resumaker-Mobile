package com.resumaker.app.component.section

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SloganSection(
    slogan: String,
    onSloganChange: (String) -> Unit,
    sloganFocusRequester: FocusRequester,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            "핵심 슬로건 및 가치관",
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        OutlinedTextField(
            value = slogan,
            onValueChange = onSloganChange,
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 100.dp)
                .focusRequester(sloganFocusRequester),
            placeholder = {
                Text(
                    "본인을 가장 잘 나타내는 한 줄 슬로건을 입력하세요.",
                    color = Color.LightGray,
                    fontSize = 14.sp
                )
            },
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
