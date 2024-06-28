package com.thomas200593.mini_retail_app.core.data.local.database.entity

import androidx.room.TypeConverter
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import ulid.ULID

@Serializable
data class Contact(
    val genId: String = ULID.randomULID(),
    val identifierKey: Int,
    val additionalInfo: String? = null,
    val mediaIdentifierKey: Int,
    val contactValue: String? = null,
    val auditTrail: AuditTrail = AuditTrail()
)

class TypeConverterContact{
    @TypeConverter
    fun toJson(contact: Contact?): String =
        Json.encodeToString(contact)

    @TypeConverter
    fun toJsonArray(contact: List<Contact>?): String =
        Json.encodeToString(contact)

    @TypeConverter
    fun fromJson(json: String): Contact? =
        Json.decodeFromString(json)

    @TypeConverter
    fun fromJsonArray(json: String): List<Contact>? =
        Json.decodeFromString(json)
}