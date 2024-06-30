package com.thomas200593.mini_retail_app.features.business.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.thomas200593.mini_retail_app.R
import com.thomas200593.mini_retail_app.app.navigation.ScreenGraphs
import com.thomas200593.mini_retail_app.core.ui.common.Icons

enum class DestinationBusinessMasterData(
    val route: String,
    @DrawableRes val iconRes: Int,
    @StringRes val title: Int,
    @StringRes val description: Int,
    val usesAuth: Boolean
){
    SUPPLIER(
        route = ScreenGraphs.BusinessMasterDataSupplier.route,
        iconRes = Icons.Supplier.supplier,
        title = R.string.str_supplier,
        description = R.string.str_supplier_desc,
        usesAuth = true
    ),
    CUSTOMER(
        route = ScreenGraphs.BusinessMasterDataCustomer.route,
        iconRes = Icons.Customer.customer,
        title = R.string.str_customer,
        description = R.string.str_customer_desc,
        usesAuth = true
    )
}