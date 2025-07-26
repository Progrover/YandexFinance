package dev.progrover.core.base.utils

import android.Manifest
import android.content.Context
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.annotation.RequiresPermission

object SettingsOptions {

    val localeVariants =
        mapOf(
            LocaleVariant.English to "en",
            LocaleVariant.Russian to "ru"
        )

    val colorVariants =
        mapOf(
            ColorVariant.Blue to "BLUE",
            ColorVariant.Green to "GREEN",
            ColorVariant.Yellow to "YELLOW",
            ColorVariant.Orange to "ORANGE",
            ColorVariant.Purple to "PURPLE",
        )
}

enum class LocaleVariant {
    Russian,
    English
}

enum class ColorVariant {
    Green,
    Yellow,
    Blue,
    Orange,
    Purple,
}

enum class HapticsVariant {
    Silent,
    Short,
    Medium,
    Long,
}

/**
 * Через HapticFeedback пробовал, почему-то не работало,
 * поэтому решил сделать через Vibrator
 */
@RequiresPermission(Manifest.permission.VIBRATE)
fun performAppHaptic(vibrationType: HapticsVariant, context: Context) {
    val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

    when (vibrationType) {
        HapticsVariant.Silent -> Unit

        HapticsVariant.Short -> {
            vibrator.vibrate(
                VibrationEffect.createOneShot(
                    70,
                    VibrationEffect.DEFAULT_AMPLITUDE
                )
            )
        }

        HapticsVariant.Medium -> {
            vibrator.vibrate(
                VibrationEffect.createOneShot(
                    130,
                    VibrationEffect.DEFAULT_AMPLITUDE
                )
            )
        }

        HapticsVariant.Long -> {
            vibrator.vibrate(
                VibrationEffect.createOneShot(
                    200,
                    VibrationEffect.DEFAULT_AMPLITUDE
                )
            )
        }
    }
}