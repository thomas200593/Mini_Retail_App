package com.thomas200593.mini_retail_app.features.business.biz_m_data.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.thomas200593.mini_retail_app.R.string.str_supplier
import com.thomas200593.mini_retail_app.R.string.str_supplier_desc
import com.thomas200593.mini_retail_app.app.navigation.ScrGraphs.Supplier
import com.thomas200593.mini_retail_app.core.ui.common.CustomIcons.Supplier.supplier

enum class DestMasterData(
    val route: String,
    @DrawableRes val iconRes: Int,
    @StringRes val title: Int,
    @StringRes val description: Int,
    val usesAuth: Boolean
){
    SUPPLIER(
        route = Supplier.route,
        iconRes = supplier,
        title = str_supplier,
        description = str_supplier_desc,
        usesAuth = true
    ),
//    CUSTOMER(
//        route = ScrGraphs.Customer.route,
//        iconRes = CustomIcons.Customer.customer,
//        title = R.string.str_customer,
//        description = R.string.str_customer_desc,
//        usesAuth = true
//    )
}