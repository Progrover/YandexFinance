package dev.progrover.core.base.utils

import androidx.navigation.NavController

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
