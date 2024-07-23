package com.thomas200593.mini_retail_app.core.data.local.database.entity_common

import androidx.room.TypeConverter
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_country.entity.Country
import kotlinx.serialization.*
import kotlinx.serialization.json.Json
import ulid.ULID.Companion.randomULID

@Serializable
data class Address(
    val genId: String = randomULID(),
    val identifierKey: Int,
    val additionalInfo: String? = null,
    val label: String? = null,
    val streetLine: String? = null,
    val postalCode: String? = null,
    val country: Country? = null,
    val auditTrail: AuditTrail? = AuditTrail()
)

class TypeConvAddress{
    @TypeConverter
    fun toJson(address: Address?): String = Json.encodeToString(value = address)

    @TypeConverter
    fun toJsonArray(address: List<Address>?): String = Json.encodeToString(value = address)

    @TypeConverter
    fun fromJson(json: String): Address? = Json.decodeFromString(json)

    @TypeConverter
    fun fromJsonArray(json: String): List<Address>? = Json.decodeFromString(json)
}