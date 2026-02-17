package com.resumaker.core.ui.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    filled: Boolean = true,
    backgroundColor: Color = Color(0xFF2161EE),
    contentColor: Color = Color.White,
    showShadow: Boolean = false
) {
    val primaryBlue = Color(0xFF2161EE)
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (filled) backgroundColor else Color.Transparent,
            contentColor = if (filled) contentColor else primaryBlue
        ),
        border = if (filled) null else BorderStroke(1.dp, primaryBlue),
        elevation = if (showShadow) {
            ButtonDefaults.buttonElevation(
                defaultElevation = 6.dp,
                pressedElevation = 3.dp,
                focusedElevation = 6.dp,
                hoveredElevation = 6.dp,
                disabledElevation = 0.dp
            )
        } else {
            ButtonDefaults.buttonElevation(
                defaultElevation = 0.dp,
                pressedElevation = 0.dp,
                focusedElevation = 0.dp,
                hoveredElevation = 0.dp,
                disabledElevation = 0.dp
            )
        }
    ) {
        Text(text = text, fontSize = 16.sp, fontWeight = FontWeight.Bold)
    }
}
