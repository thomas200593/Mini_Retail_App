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
    val uri: String? = null,
    val label: String? = null,
    val auditTrail: AuditTrail = AuditTrail()
)

class TypeConvLinks{
    @TypeConverter
    fun toJson(link: Link?): String = Json.encodeToString(value = link)

    @TypeConverter
    fun toJsonArray(link: List<Link>?): String = Json.encodeToString(value = link)

    @TypeConverter
    fun fromJson(json: String): Link? = Json.decodeFromString(json)

    @TypeConverter
    fun fromJsonArray(json: String): List<Link>? = Json.decodeFromString(json)
}