package com.resumaker.core.designsystem

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Immutable

/**
 * 이력서·페르소나 카드 공통 아이콘 타입.
 * 기존 PNG drawable 리소스와 1:1 매핑됩니다.
 */
@Immutable
enum class IconType(
    val value: String,
    @param:DrawableRes val drawableResId: Int
) {
    Dev("dev", R.drawable.dev),
    Energy("energy", R.drawable.energy),
    Hr("hr", R.drawable.hr),
    Language("language", R.drawable.language),
    OrangeRocket("orange_rocket", R.drawable.orange_rocket),
    PurpleRocket("purple_rocket", R.drawable.purple_rocket),
    Statistic("statistic", R.drawable.statistic),
    Warm("warm", R.drawable.warm);

    companion object {
        private val STRING_MAP = mapOf(
            "persona1" to Warm, "persona2" to Warm, "persona3" to Warm,
            "doc" to Dev, "code" to Dev, "design" to Dev, "custom" to Warm, "default" to Dev
        )
        fun fromString(value: String?): IconType = value?.let { STRING_MAP[it] ?: entries.find { e -> e.value == it } } ?: Dev
    }
}
