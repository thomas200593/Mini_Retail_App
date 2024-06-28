package com.thomas200593.mini_retail_app.features.business.entity.dto

import kotlinx.serialization.Serializable

@Serializable
data class BizIdName(
    val legalName: String? = null,
    val commonName: String? = null
)