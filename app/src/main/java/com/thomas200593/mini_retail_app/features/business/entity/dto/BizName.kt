package com.thomas200593.mini_retail_app.features.business.entity.dto

import com.thomas200593.mini_retail_app.core.data.local.database.entity_common.AuditTrail
import kotlinx.serialization.Serializable

@Serializable
data class BizName(
    val legalName: String? = null,
    val commonName: String? = null,
    val auditTrail: AuditTrail = AuditTrail()
)