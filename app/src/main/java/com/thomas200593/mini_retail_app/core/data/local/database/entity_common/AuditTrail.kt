package com.thomas200593.mini_retail_app.core.data.local.database.entity_common

import androidx.room.TypeConverter
import com.thomas200593.mini_retail_app.core.design_system.util.Constants.NOW_EPOCH_SECOND
import com.thomas200593.mini_retail_app.core.design_system.util.Constants.SYSTEM
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
data class AuditTrail(
    val createdBy: String = SYSTEM,
    val createdAt: Long = NOW_EPOCH_SECOND,
    val modifiedBy: String = SYSTEM,
    val modifiedAt: Long = NOW_EPOCH_SECOND
)

class TypeConvAuditTrail{
    @TypeConverter
    fun toJson(auditTrail: AuditTrail): String =
        runCatching { Json.encodeToString(value = auditTrail) }
            .getOrElse { throw it }

    @TypeConverter
    fun fromJson(json: String): AuditTrail =
        runCatching { Json.decodeFromString<AuditTrail>(json) }
            .getOrElse { AuditTrail() }
}