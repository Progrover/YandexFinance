package dev.progrover.core.base.utils

import androidx.navigation.NavController
import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import java.text.NumberFormat
import java.util.Locale

fun String.isUnicode(): Boolean =
    !this.contains("[А-Яа-яA-Za-z0-9!\"#%&'()*+,-./:;\\\\<=>?@\\[\\]^_`{|}~]".toRegex())

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

fun String.getCurrency(withSpace: Boolean = true) =
    when (this) {
        "RUB" -> "₽"
        "USD" -> "$"
        "EUR" -> "€"
        else -> " ???"
    }.let { if (withSpace) " $it" else it }

/**
 * Расширения для передачи сериализованных объектов через аргументы навигации.
 * Нужны для замены символов, которые являются специальными символами навигации
 * и могут привести к ошибке при десериализации
 */
fun String.toRouteArgument(): String =
    URLEncoder.encode(this, StandardCharsets.UTF_8.toString())

fun String.fromRouteArgument(): String =
    URLDecoder.decode(this, StandardCharsets.UTF_8.toString())