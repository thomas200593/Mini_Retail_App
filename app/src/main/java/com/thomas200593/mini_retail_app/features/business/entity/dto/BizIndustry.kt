package com.thomas200593.mini_retail_app.features.business.entity.dto

import com.thomas200593.mini_retail_app.core.data.local.database.entity.AuditTrail
import kotlinx.serialization.Serializable

@Serializable
data class BizIndustry(
    val identityKey: Int,
    val identityValue: Int,
    val additionalInfo: String? = null,
    val auditTrail: AuditTrail = AuditTrail()
)