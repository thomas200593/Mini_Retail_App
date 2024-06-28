package com.thomas200593.mini_retail_app.core.data.local.database.entity

import com.thomas200593.mini_retail_app.features.app_config.entity.Country
import kotlinx.serialization.Serializable

@Serializable
data class Taxation(
    val identifierKey: Int,
    val taxIdDocNumber: String? = null,
    val taxIssuerCountry: Country? = null,
    val taxRatePercentage: Double = 0.00,
    val taxIncluded: Boolean = false,
    val auditTrail: AuditTrail = AuditTrail()
)