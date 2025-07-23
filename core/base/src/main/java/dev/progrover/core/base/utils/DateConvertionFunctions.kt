package dev.progrover.core.base.utils

import android.content.Context
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAdjusters
import java.util.Locale

fun Long.dateToServerRequest(): String = Instant.ofEpochMilli(this)
    .atZone(ZoneId.systemDefault())
    .toLocalDate()
    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))

fun String.serverRequestToMillisStartOfDay(): Long {
    val localDate = LocalDate.parse(this)
    val zone = ZoneId.systemDefault()
    return localDate.atStartOfDay(zone).toInstant().toEpochMilli()
}

fun String.serverRequestToMillisEndOfDay(): Long {
    val localDate = LocalDate.parse(this)
    val zone = ZoneId.systemDefault()
    return localDate.plusDays(1).atStartOfDay(zone).toInstant().toEpochMilli() - 1
}

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

fun getStartOfToday(): Long {
    val today = LocalDate.now()
    val zone = ZoneId.systemDefault()
    val startOfDay = today.atStartOfDay(zone).toInstant().toEpochMilli()

    return startOfDay
}

fun getEndOfToday(): Long {
    val today = LocalDate.now()
    val zone = ZoneId.systemDefault()
    val endOfDay = today.plusDays(1).atStartOfDay(zone).toInstant().toEpochMilli() - 1

    return endOfDay
}

fun Long.monthAndYear(context: Context): String {
    val locale = context.resources.configuration.locales.get(0)

    val monthMapRu = mapOf(
        "JANUARY" to "Январь",
        "FEBRUARY" to "Февраль",
        "MARCH" to "Март",
        "APRIL" to "Апрель",
        "MAY" to "Май",
        "JUNE" to "Июнь",
        "JULY" to "Июль",
        "AUGUST" to "Август",
        "SEPTEMBER" to "Сентябрь",
        "OCTOBER" to "Октябрь",
        "NOVEMBER" to "Ноябрь",
        "DECEMBER" to "Декабрь"
    )

    val monthMapEn = mapOf(
        "JANUARY" to "January",
        "FEBRUARY" to "February",
        "MARCH" to "March",
        "APRIL" to "April",
        "MAY" to "May",
        "JUNE" to "June",
        "JULY" to "July",
        "AUGUST" to "August",
        "SEPTEMBER" to "September",
        "OCTOBER" to "October",
        "NOVEMBER" to "November",
        "DECEMBER" to "December"
    )

    val date =
        Instant.ofEpochMilli(this)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()

    val month = if (locale.language == SettingsOptions.localeVariants[LocaleVariant.English])
        monthMapEn[date.month.name] else monthMapRu[date.month.name]
    return "$month ${date.year}"
}

fun getStartOfMonth(timeMillis: Long): Long {
    val zone = ZoneId.systemDefault()
    val dateTime = Instant.ofEpochMilli(timeMillis).atZone(zone)
    val startOfMonth = dateTime.with(TemporalAdjusters.firstDayOfMonth())
        .withHour(0)
        .withMinute(0)
        .withSecond(0)
        .withNano(0)
    return startOfMonth.toInstant().toEpochMilli()
}

fun getEndOfMonth(timeMillis: Long): Long {
    val zone = ZoneId.systemDefault()
    val dateTime = Instant.ofEpochMilli(timeMillis).atZone(zone)
    val endOfMonth = dateTime.with(TemporalAdjusters.lastDayOfMonth())
        .withHour(23)
        .withMinute(59)
        .withSecond(59)
        .withNano(999_000_000)
    return endOfMonth.toInstant().toEpochMilli()
}