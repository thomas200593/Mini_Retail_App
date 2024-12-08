package com.thomas200593.mini_retail_app.features.business.biz_profile.entity

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
    val bizName: BizName,
    val industries: Industries,
    val legalType: LegalType,
    val taxation: Taxation,
    val auditTrail: AuditTrail = AuditTrail()
)

class TypeConvBizIdentity{
    @TypeConverter
    fun toJson(bizIdentity: BizIdentity): String =
        Json.encodeToString(bizIdentity)

    @TypeConverter
    fun fromJson(json: String): BizIdentity =
        Json.decodeFromString(json)
}