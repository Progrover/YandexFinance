package dev.progrover.core.base.utils

import androidx.navigation.NavController
import java.text.NumberFormat
import java.util.Locale

fun String.isUnicode(): Boolean =
    !this.contains("[А-Яа-яA-Za-z0-9!\"#$%&'()*+,-./:;\\\\<=>?@\\[\\]^_`{|}~]".toRegex())

fun NavController.customNavigate(route: String) {
    this.navigate(route) {
        popUpTo(route) {
            inclusive = true
        }
        launchSingleTop = true
    }
}

fun Double.formatToAmount() =
    NumberFormat.getInstance(Locale.US).format(this).replace(",", " ")

fun String.formatToAmount() =
    NumberFormat.getInstance(Locale.US).format(this.toDouble()).replace(",", " ")

fun String.addCurrency(currency: String) = this.plus(
    when (currency) {
        "RUB" -> " ₽"
        "USD" -> " $"
        "EUR" -> " €"
        else -> " ???"
    }
)

fun String.getCurrency() =
    when (this) {
        "RUB" -> " ₽"
        "USD" -> " $"
        "EUR" -> " €"
        else -> " ???"
    }
