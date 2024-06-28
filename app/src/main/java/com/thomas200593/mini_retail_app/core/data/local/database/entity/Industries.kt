package com.thomas200593.mini_retail_app.core.data.local.database.entity

import kotlinx.serialization.Serializable

@Serializable
data class Industries(
    val identityKey: Int,
    val additionalInfo: String? = null,
    val auditTrail: AuditTrail = AuditTrail()
)