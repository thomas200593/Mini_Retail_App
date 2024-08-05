package com.thomas200593.mini_retail_app.features.business.biz_profile.entity.dto

import com.thomas200593.mini_retail_app.core.data.local.database.entity_common.AuditTrail
import com.thomas200593.mini_retail_app.core.data.local.database.entity_common.Industries
import com.thomas200593.mini_retail_app.features.business.biz_profile.entity.BizName
import kotlinx.serialization.Serializable

@Serializable
data class BizProfileSummary(
    val seqId: Int,
    val genId: String,
    val bizName: BizName?,
    val bizIndustry: Industries?,
    val auditTrail: AuditTrail
)