package com.thomas200593.mini_retail_app.core.data.local.database.entity

import kotlinx.serialization.Serializable

@Serializable
data class LegalDocumentType(
    val identifierKey: Int,
    val additionalInfo: String? = null,
    val auditTrail: AuditTrail = AuditTrail()
)