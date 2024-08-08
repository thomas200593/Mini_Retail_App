package com.thomas200593.mini_retail_app.core.design_system.util.datetime

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import javax.inject.Inject

enum class DateFormat(val formatter: DateTimeFormatter) {
    LONG_DATETIME_WITH_TZ(DateTimeFormatter.ofPattern("EEEE, dd-MMM-yyyy HH:mm:ss z")),
    ISO8601(DateTimeFormatter.ISO_OFFSET_DATE_TIME)
}

interface HlpDateTime {
    fun convertTimestampToDate(
        timestamp: Long,
        offsetString: String? = ZoneOffset.UTC.toString(),
        format: DateFormat
    ): String
}

class HlpDateTimeImpl @Inject constructor(): HlpDateTime {
    override fun convertTimestampToDate(
        timestamp: Long,
        offsetString: String?,
        format: DateFormat
    ): String {
        val zoneOffset = offsetString?.let {
            val offsetPattern = Regex("^[+-](0[0-9]|1[0-4]):([0-5][0-9])$")
            if (offsetPattern.matches(it)) ZoneOffset.of(it) else ZoneOffset.UTC
        } ?: ZoneOffset.UTC // Default to +00:00 if offsetString is null

        return LocalDateTime
            .ofInstant(Instant.ofEpochSecond(timestamp), zoneOffset)
            .atOffset(zoneOffset)
            .format(format.formatter)
    }
}