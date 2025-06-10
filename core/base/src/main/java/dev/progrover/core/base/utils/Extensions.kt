package dev.progrover.core.base.utils

fun String.isUnicode() : Boolean = !this.contains("[А-Яа-яA-Za-z0-9!\"#$%&'()*+,-./:;\\\\<=>?@\\[\\]^_`{|}~]".toRegex())