package com.thomas200593.mini_retail_app.features.business.entity.business_profile.dto

import com.thomas200593.mini_retail_app.core.data.local.database.entity_common.AuditTrail
import com.thomas200593.mini_retail_app.core.data.local.database.entity_common.Industries
import com.thomas200593.mini_retail_app.features.business.entity.business_profile.BizName
import kotlinx.serialization.Serializable

@Serializable
data class BizProfileSummary(
    val seqId: Int,
    val genId: String,
    val bizName: BizName?,
    val bizIndustry: Industries?,
    val auditTrail: AuditTrail
)