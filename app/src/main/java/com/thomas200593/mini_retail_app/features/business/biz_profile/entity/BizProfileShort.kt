package com.thomas200593.mini_retail_app.features.business.biz_profile.entity

import com.thomas200593.mini_retail_app.core.data.local.database.entity_common.AuditTrail
import com.thomas200593.mini_retail_app.core.data.local.database.entity_common.Industries
import com.thomas200593.mini_retail_app.core.data.local.database.entity_common.LegalType
import com.thomas200593.mini_retail_app.core.data.local.database.entity_common.Taxation
import kotlinx.serialization.Serializable

@Serializable
data class BizProfileShort(
    val seqId: Int,
    val genId: String,
    val bizName: BizName,
    val bizIndustry: Industries,
    val bizLegalType: LegalType,
    val bizTaxation: Taxation,
    val auditTrail: AuditTrail
)

fun BizProfileShort.toBizProfile() = BizProfile(
    seqId = seqId,
    genId = genId,
    bizIdentity = BizIdentity(
        bizName = bizName,
        industries = bizIndustry,
        legalType = bizLegalType,
        taxation = bizTaxation,
        auditTrail = auditTrail
    ),
    auditTrail = auditTrail
)