package dev.progrover.core.base.model

import androidx.annotation.DrawableRes

/**
 * Класс, связывающий все элементы, которые можно отобразить в виде списка
 */
abstract class ListItem(
    open val id: Int? = null,
    open val name: String,
    val caption: String? = null,
    @DrawableRes val iconResId: Int? = null
)