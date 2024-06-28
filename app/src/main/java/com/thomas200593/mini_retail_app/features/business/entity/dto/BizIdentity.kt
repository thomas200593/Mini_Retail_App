package com.thomas200593.mini_retail_app.features.business.entity.dto

import androidx.room.TypeConverter
import com.thomas200593.mini_retail_app.core.data.local.database.entity_common.AuditTrail
import com.thomas200593.mini_retail_app.core.data.local.database.entity_common.Industries
import com.thomas200593.mini_retail_app.core.data.local.database.entity_common.LegalType
import com.thomas200593.mini_retail_app.core.data.local.database.entity_common.Taxation
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
data class BizIdentity(
    val bizName: BizName? = null,
    val industries: Industries? = null,
    val legalType: LegalType? = null,
    val taxation: Taxation? = null,
    val auditTrail: AuditTrail = AuditTrail()
)

class TypeConverterBizIdentity{
    @TypeConverter
    fun toJson(bizIdentity: BizIdentity?): String =
        Json.encodeToString(bizIdentity)

    @TypeConverter
    fun fromJson(json: String): BizIdentity? =
        Json.decodeFromString(json)
}