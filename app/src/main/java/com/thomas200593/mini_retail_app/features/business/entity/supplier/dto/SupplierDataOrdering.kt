package com.thomas200593.mini_retail_app.features.business.entity.supplier.dto

import com.thomas200593.mini_retail_app.features.business.entity.supplier.Supplier

enum class SupplierDataOrdering(
    val key: String,
    val directionAsc: Int,
    val label: String
) {
    GEN_ID_ASC(
        key = Supplier::class.objectInstance?.genId.toString(),
        directionAsc = 1,
        label = "ID (A-Z)"
    ),
    GEN_ID_DESC(
        key = Supplier::class.objectInstance?.genId.toString(),
        directionAsc = 0,
        label = "ID (Z-A)"
    ),
    LEGAL_NAME_ASC(
        key = Supplier::class.objectInstance?.sprLegalName.toString(),
        directionAsc = 1,
        label = "Legal Name (A-Z)"
    ),
    LEGAL_NAME_DESC(
        key = Supplier::class.objectInstance?.sprLegalName.toString(),
        directionAsc = 1,
        label = "Legal Name (Z-A)"
    )
}