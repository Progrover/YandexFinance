package dev.progrover.articles.impl.presentation.contract.articles

import androidx.annotation.StringRes
import dev.progrover.core.base.presentation.mvi.UIEffect

/**
 * Класс, хранящий все эффекты articles feature
 */
sealed class ArticlesUIEffect : UIEffect {

    class ShowError(@StringRes val messageResId: Int) : ArticlesUIEffect()
}