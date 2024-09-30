package com.thomas200593.mini_retail_app.core.data.local.database.entity_common

import androidx.room.TypeConverter
import com.thomas200593.mini_retail_app.core.design_system.util.HlpCountry
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_country.entity.Country
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import ulid.ULID.Companion.randomULID

@Serializable
data class Address(
    val genId: String = randomULID(),
    val identifierKey: Int,
    val additionalInfo: String = String(),
    val label: String = String(),
    val streetLine: String = String(),
    val postalCode: String = String(),
    val country: Country = HlpCountry.COUNTRY_DEFAULT,
    val auditTrail: AuditTrail = AuditTrail()
)

class TypeConvAddress {
    @TypeConverter
    fun toJson(address: Address): String =
        runCatching { Json.encodeToString(value = address) }
            .getOrElse { throw it }

    @TypeConverter
    fun toJsonArray(address: List<Address>): String =
        runCatching { Json.encodeToString(value = address) }
            .getOrElse { throw it }

    @TypeConverter
    fun fromJson(json: String): Address =
        runCatching { Json.decodeFromString<Address>(json) }
            .getOrElse { throw it }

    @TypeConverter
    fun fromJsonArray(json: String): List<Address> =
        runCatching { Json.decodeFromString<List<Address>>(json) }
            .getOrElse { throw it }
}