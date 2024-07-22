package com.thomas200593.mini_retail_app.features.business.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.thomas200593.mini_retail_app.R
import com.thomas200593.mini_retail_app.app.navigation.ScrGraphs
import com.thomas200593.mini_retail_app.core.ui.common.CustomIcons

enum class DestinationBusiness(
    val route: String,
    @DrawableRes val iconRes: Int,
    @StringRes val title: Int,
    @StringRes val description: Int,
    val usesAuth: Boolean
) {
    MASTER_DATA(
        route = ScrGraphs.MasterData.route,
        iconRes = CustomIcons.Data.master_data,
        title = R.string.str_biz_master_data,
        description = R.string.str_biz_master_data_desc,
        usesAuth = true
    ),
//    BUSINESS_PROFILE(
//        route = ScrGraphs.BusinessProfile.route,
//        iconRes = CustomIcons.Business.business_profile,
//        title = R.string.business_profile,
//        description = R.string.business_profile_desc,
//        usesAuth = true
//    )
}