package com.resumaker.core.designsystem

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

/**
 * 앱 고유의 타이포그래피.
 * MaterialTheme Typography를 래핑하여 비즈니스 디자인 언어를 보호합니다.
 */
object AppTypography {
    val headlineLarge = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp
    )
    val headlineMedium = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 28.sp
    )
    val titleLarge = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 22.sp
    )
    val titleMedium = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp
    )
    val titleSmall = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp
    )
    val bodyLarge = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    )
    val bodyMedium = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    )
    val bodySmall = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 13.sp
    )
    val labelLarge = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp
    )
    val labelSmall = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
}

/** CompositionLocal: 앱 타이포그래피 */
val LocalAppTypography = staticCompositionLocalOf { AppTypography }
