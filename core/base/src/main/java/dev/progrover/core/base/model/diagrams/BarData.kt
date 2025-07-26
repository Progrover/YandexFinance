package dev.progrover.core.base.model.diagrams

data class BarData(
    val value: Float,
    val description: String,
    val caption: String = "",
    val legend: String = ""
)