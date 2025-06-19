package dev.progrover.core.base.utils

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

fun Long.toServerRequest(): String = Instant.ofEpochMilli(this)
    .atZone(ZoneId.systemDefault())
    .toLocalDate()
    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))

fun Long.toDatePresentation(): String = Instant.ofEpochMilli(this)
    .atZone(ZoneId.systemDefault())
    .toLocalDate()
    .format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))

fun getFirstDayOfCurrentMonthForPresentation(): Long = LocalDate.now()
    .withDayOfMonth(1)
    .atTime(12, 0)
    .atZone(ZoneId.systemDefault())
    .toInstant()
    .toEpochMilli()

fun getRealFirstDayOfCurrentMonth(): Long = LocalDate.now()
    .withDayOfMonth(1)
    .atStartOfDay(ZoneId.systemDefault())
    .toInstant()
    .toEpochMilli()

fun Long.toDateTimePresentation(): String =
    Instant.ofEpochMilli(this)
        .atZone(ZoneId.systemDefault())
        .format(DateTimeFormatter.ofPattern("dd.MM.yy HH:mm"))

fun String.toMillis() = Instant.parse(this).toEpochMilli()

