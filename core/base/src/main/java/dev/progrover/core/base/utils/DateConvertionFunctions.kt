package dev.progrover.core.base.utils

import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

fun Long.dateToServerRequest(): String = Instant.ofEpochMilli(this)
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

fun getCurrentDayForPicker(): Long =
    LocalDate.now()
        .atTime(12, 0)
        .atZone(ZoneId.systemDefault())
        .toInstant()
        .toEpochMilli()

fun Long.extractTimeFromIsoString(removeSymbols: Boolean = true): String {
    val formatter = DateTimeFormatter.ofPattern("HH:mm", Locale.getDefault())
    return Instant.ofEpochMilli(this)
        .atZone(ZoneId.systemDefault())
        .format(formatter).let {
            if (removeSymbols) it.replace(":", "") else it
        }
}

fun getRealFirstDayOfCurrentMonth(): Long = LocalDate.now()
    .withDayOfMonth(1)
    .atStartOfDay(ZoneId.systemDefault())
    .toInstant()
    .toEpochMilli()

fun Long.toDateTimePresentation(): String =
    Instant.ofEpochMilli(this)
        .atZone(ZoneId.systemDefault())
        .format(DateTimeFormatter.ofPattern("dd.MM.yy HH:mm"))

fun String.formatInputAsTime(): String {
    val digits = this.filter { it.isDigit() }.takeLast(4).padStart(4, '0')
    val hours = digits.substring(0, 2)
    val minutes = digits.substring(2, 4)
    return "$hours:$minutes"
}

fun String.inputTimeCorrect(): Boolean {
    val digits = this.filter { it.isDigit() }.takeLast(4).padStart(4, '0')
    val hours = digits.substring(0, 2)
    val minutes = digits.substring(2, 4)
    return hours.toInt() < 24 && minutes.toInt() < 60
}

fun String.toMillis() = Instant.parse(this).toEpochMilli()

fun combineDateAndTime(dateMillis: Long, time: String): Long {
    val zone = ZoneId.systemDefault()
    val localDate = Instant.ofEpochMilli(dateMillis)
        .atZone(zone)
        .toLocalDate()
    val formatter = DateTimeFormatter.ofPattern("HH:mm")
    val localTime = LocalTime.parse(time, formatter)
    val dateTime = LocalDateTime.of(localDate, localTime)
    return dateTime.atZone(zone).toInstant().toEpochMilli()
}

fun Long.formatToIsoUtc(): String {
    val instant = Instant.ofEpochMilli(this)
    return DateTimeFormatter.ISO_INSTANT.format(instant)
}
