package com.thomas200593.mini_retail_app.features.business.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.thomas200593.mini_retail_app.core.data.local.database.entity_common.Address
import com.thomas200593.mini_retail_app.core.data.local.database.entity_common.AuditTrail
import com.thomas200593.mini_retail_app.core.design_system.base_class.BaseEntity
import com.thomas200593.mini_retail_app.core.data.local.database.entity_common.Contact
import com.thomas200593.mini_retail_app.core.data.local.database.entity_common.Link
import com.thomas200593.mini_retail_app.core.data.local.database.entity_common.TypeConverterAuditTrail
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
    override val seqId: Int = 0,

    @ColumnInfo(name = "gen_id")
    override val genId: String = ULID.randomULID(),

    @ColumnInfo(name = "audit_trail")
    @TypeConverters(TypeConverterAuditTrail::class)
    override val auditTrail: AuditTrail = AuditTrail(),

    @ColumnInfo(name = "biz_identity")
    val bizIdentity: BizIdentity? = null,

    @ColumnInfo(name = "addresses")
    val addresses: List<Address>? = null,

    @ColumnInfo(name = "contacts")
    val contacts: List<Contact>? = null,

    @ColumnInfo(name = "links")
    val links: List<Link>? = null,
): BaseEntity