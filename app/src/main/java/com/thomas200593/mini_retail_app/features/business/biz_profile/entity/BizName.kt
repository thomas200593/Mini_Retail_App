package com.thomas200593.mini_retail_app.features.business.biz_profile.entity

import androidx.room.TypeConverter
import com.thomas200593.mini_retail_app.core.data.local.database.entity_common.AuditTrail
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
data class BizName(
    val legalName: String? = null,
    val commonName: String? = null,
    val auditTrail: AuditTrail = AuditTrail()
)

class TypeConvBizName{
    @TypeConverter
    fun toJson(bizName: BizName?): String = Json.encodeToString(value = bizName)

    @TypeConverter
    fun fromJson(json: String): BizName? = Json.decodeFromString(json)
}