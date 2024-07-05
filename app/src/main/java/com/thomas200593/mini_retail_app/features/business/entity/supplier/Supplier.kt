package com.thomas200593.mini_retail_app.features.business.entity.supplier

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.thomas200593.mini_retail_app.core.data.local.database.entity_common.AuditTrail
import com.thomas200593.mini_retail_app.core.data.local.database.entity_common.Contact
import com.thomas200593.mini_retail_app.core.design_system.base_class.BaseEntity
import com.thomas200593.mini_retail_app.features.business.entity.business_profile.BizIdentity
import kotlinx.serialization.Serializable

@Serializable
@Entity(
    tableName = "supplier"
)
data class Supplier(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "seq_id")
    override val seqId: Int,

    @ColumnInfo(name = "gen_id")
    override val genId: String,

    @ColumnInfo(name = "audit_trail")
    override val auditTrail: AuditTrail = AuditTrail(),

    @ColumnInfo(name = "spr_legal_name")
    val sprLegalName: String? = String(),

    @ColumnInfo(name = "spr_biz_contacts")
    val sprBizContacts: List<Contact>? = null
): BaseEntity