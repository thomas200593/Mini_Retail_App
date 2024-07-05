package com.thomas200593.mini_retail_app.features.business.entity.supplier.dto

enum class SupplierDataOrdering(
    val label: String
) {
    GEN_ID_ASC(
        label = "ID (A-Z)"
    ),
    GEN_ID_DESC(
        label = "ID (Z-A)"
    ),
    LEGAL_NAME_ASC(
        label = "Legal Name (A-Z)"
    ),
    LEGAL_NAME_DESC(
        label = "Legal Name (Z-A)"
    )
}