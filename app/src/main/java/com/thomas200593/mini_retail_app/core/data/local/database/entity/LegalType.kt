package com.thomas200593.mini_retail_app.core.data.local.database.entity

import kotlinx.serialization.Serializable

@Serializable
data class LegalType(
    val identifierKey: Int,
    val additionalInfo: String? = null,
    val legalDocumentType: LegalDocumentType? = null,
    val auditTrail: AuditTrail = AuditTrail()
)
