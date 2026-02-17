package com.resumaker.core.designsystem

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable

/**
 * 도메인 중심 디자인 시스템.
 * AppColors, AppTypography, AppSpacing을 CompositionLocal로 제공합니다.
 */
object DesignSystem {
    val colors: AppColors
        @Composable
        @ReadOnlyComposable
        get() = LocalAppColors.current

    val spacing: AppSpacing
        @Composable
        @ReadOnlyComposable
        get() = LocalAppSpacing.current

    val typography: AppTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalAppTypography.current
}

/**
 * DesignSystem CompositionLocal을 제공하는 래퍼.
 */
@Composable
fun DesignSystemProvider(
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalAppColors provides AppColors,
        LocalAppSpacing provides AppSpacing,
        LocalAppTypography provides AppTypography,
        content = content
    )
}
