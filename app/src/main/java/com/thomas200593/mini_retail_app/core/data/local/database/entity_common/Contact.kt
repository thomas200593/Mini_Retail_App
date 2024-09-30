package com.thomas200593.mini_retail_app.core.data.local.database.entity_common

import androidx.room.TypeConverter
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import ulid.ULID.Companion.randomULID

@Serializable
data class Contact(
    val genId: String = randomULID(),
    val identifierKey: Int,
    val additionalInfo: String = String(),
    val label: String = String(),
    val mediaIdentifierKey: Int,
    val contactValue: String = String(),
    val auditTrail: AuditTrail = AuditTrail()
)

class TypeConvContact{
    @TypeConverter
    fun toJson(contact: Contact): String =
        runCatching { Json.encodeToString(value = contact) }
            .getOrElse { throw it }

    @TypeConverter
    fun toJsonArray(contact: List<Contact>): String =
        runCatching { Json.encodeToString(value = contact) }
            .getOrElse { throw it }

    @TypeConverter
    fun fromJson(json: String): Contact =
        runCatching { Json.decodeFromString<Contact>(json) }
            .getOrElse { throw it }

    @TypeConverter
    fun fromJsonArray(json: String): List<Contact> =
        runCatching { Json.decodeFromString<List<Contact>>(json) }
            .getOrElse { throw it }
}