package com.thomas200593.mini_retail_app.core.data.local.database.entity_common

import androidx.room.TypeConverter
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import ulid.ULID.Companion.randomULID

@Serializable
data class Link(
    val genId: String = randomULID(),
    val identifierKey: Int,
    val uri: String = String(),
    val label: String = String(),
    val auditTrail: AuditTrail = AuditTrail()
)

class TypeConvLinks{
    @TypeConverter
    fun toJson(link: Link?): String =
        runCatching { Json.encodeToString(value = link) }
            .getOrElse { throw it }

    @TypeConverter
    fun toJsonArray(link: List<Link>): String =
        runCatching { Json.encodeToString(value = link) }
            .getOrElse { throw it }

    @TypeConverter
    fun fromJson(json: String): Link =
        runCatching { Json.decodeFromString<Link>(json) }
            .getOrElse { throw it }

    @TypeConverter
    fun fromJsonArray(json: String): List<Link> =
        runCatching { Json.decodeFromString<List<Link>>(json) }
            .getOrElse { throw it }
}