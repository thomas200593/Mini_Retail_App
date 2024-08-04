package com.thomas200593.mini_retail_app.features.business.biz_m_data_supplier.entity.dto

enum class SortSupplier(
    val title: String
) {
    GEN_ID_ASC(title = "ID (A-Z)"),
    GEN_ID_DESC(title = "ID (Z-A)"),
    LEGAL_NAME_ASC(title = "Name (A-Z)"),
    LEGAL_NAME_DESC(title = "Name (Z-A)")
}