package com.thomas200593.mini_retail_app.features.business.entity.dto

import androidx.room.TypeConverter
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
data class BizIdentity(
    val bizIdName: BizIdName? = null,
    val bizIndustry: BizIndustry? = null
)

class TypeConverterBizIdentity{
    @TypeConverter
    fun toJson(bizIdentity: BizIdentity?): String =
        Json.encodeToString(bizIdentity)

    @TypeConverter
    fun fromJson(json: String): BizIdentity? =
        Json.decodeFromString(json)
}