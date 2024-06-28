package com.thomas200593.mini_retail_app.core.data.local.database.entity_common

import androidx.room.TypeConverter
import com.thomas200593.mini_retail_app.core.design_system.util.Constants
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
data class AuditTrail(
    val createdBy: String = Constants.SYSTEM,
    val createdAt: Long = Constants.NOW_EPOCH_SECOND,
    val modifiedBy: String = Constants.SYSTEM,
    val modifiedAt: Long = Constants.NOW_EPOCH_SECOND
)

class TypeConverterAuditTrail{
    @TypeConverter
    fun toJson(auditTrail: AuditTrail?): String =
        Json.encodeToString(auditTrail)

    @TypeConverter
    fun fromJson(json: String): AuditTrail? =
        Json.decodeFromString(json)
}