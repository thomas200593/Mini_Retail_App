package com.thomas200593.mini_retail_app.features.business.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.thomas200593.mini_retail_app.core.data.local.database.entity.AuditTrail
import com.thomas200593.mini_retail_app.core.data.local.database.entity.TypeConverterAuditTrail
import com.thomas200593.mini_retail_app.features.business.entity.dto.BizIdentity
import kotlinx.serialization.Serializable
import ulid.ULID

@Serializable
@Entity(
    tableName = "business_profile",
    indices = [
        Index(value = ["gen_id"]),
    ]
)
data class BusinessProfile(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "seq_id")
    val seqId: Int = 0,

    @ColumnInfo(name = "gen_id")
    val genId: String = ULID.randomULID(),

    @ColumnInfo(name = "biz_identity")
    val bizIdentity: BizIdentity?,

    @ColumnInfo(name = "audit_trail")
    @TypeConverters(TypeConverterAuditTrail::class)
    val auditTrail: AuditTrail = AuditTrail()
)