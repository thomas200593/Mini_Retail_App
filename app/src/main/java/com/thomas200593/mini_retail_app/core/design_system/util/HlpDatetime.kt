package com.thomas200593.mini_retail_app.core.design_system.util

import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_timezone.entity.Timezone
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId.getAvailableZoneIds
import java.time.ZoneId.of
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatter.ISO_OFFSET_DATE_TIME
import java.time.format.DateTimeFormatter.ofPattern
import java.util.Locale.getDefault

object HlpDatetime {
    enum class DateFormat(val formatter: DateTimeFormatter) {
        LONG_DATETIME_WITH_TZ(ofPattern("EEEE, dd-MMM-yyyy HH:mm:ss z")),
        ISO8601(ISO_OFFSET_DATE_TIME)
    }
    private const val ZULU = "+00:00"
    val TIMEZONE_DEFAULT = Timezone(ZULU)
    private const val SECONDS_IN_HOUR = 3_600
    private const val MINUTES_IN_HOUR = 60
    suspend fun getTimezoneList() = withContext(Dispatchers.IO) {
        val timezones = getAvailableZoneIds()
        val uniqueOffset = HashSet<Timezone>()
        uniqueOffset.add(Timezone(ZULU))
        for (id in timezones) {
            val timezone = of(id)
            val offset = timezone.rules.getOffset(Instant.now())
            val hours = offset.totalSeconds / SECONDS_IN_HOUR
            val minutes = (offset.totalSeconds % SECONDS_IN_HOUR) / MINUTES_IN_HOUR
            val formattedOffset =
                if (offset.totalSeconds < 0) { String.format(getDefault(), "-%02d:%02d", -hours, -minutes) }
                else { String.format(getDefault(), "+%02d:%02d", hours, minutes) }
            uniqueOffset.add(Timezone(formattedOffset))
        }
        uniqueOffset.toList().sortedBy { it.timezoneOffset }
    }

    fun timestampToDatetime(
        timestamp: Long,
        offsetString: String = ZULU,
        format: DateFormat = DateFormat.LONG_DATETIME_WITH_TZ
    ): String {
        val zoneOffset = offsetString.let {
            val offsetPattern = Regex("^[+-](0[0-9]|1[0-4]):([0-5][0-9])$")
            if (offsetPattern.matches(it)) ZoneOffset.of(it) else ZoneOffset.UTC
        } ?: ZoneOffset.UTC // Default to +00:00 if offsetString is null

        return LocalDateTime
            .ofInstant(Instant.ofEpochSecond(timestamp), zoneOffset)
            .atOffset(zoneOffset)
            .format(format.formatter)
    }
}