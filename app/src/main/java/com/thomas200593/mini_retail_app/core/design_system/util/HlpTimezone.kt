package com.thomas200593.mini_retail_app.core.design_system.util

import com.thomas200593.mini_retail_app.features.app_conf._g_timezone.entity.Timezone
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.Instant
import java.time.ZoneId.getAvailableZoneIds
import java.time.ZoneId.of
import java.util.Locale.getDefault

object HlpTimezone {
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
}