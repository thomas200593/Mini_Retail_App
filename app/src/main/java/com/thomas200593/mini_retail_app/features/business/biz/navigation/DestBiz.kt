package com.thomas200593.mini_retail_app.features.business.biz.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.thomas200593.mini_retail_app.R.string.str_biz_master_data
import com.thomas200593.mini_retail_app.R.string.str_biz_master_data_desc
import com.thomas200593.mini_retail_app.app.navigation.ScrGraphs.MasterData
import com.thomas200593.mini_retail_app.core.ui.common.CustomIcons.Data.master_data

enum class DestBiz(
    val route: String,
    @DrawableRes val iconRes: Int,
    @StringRes val title: Int,
    @StringRes val description: Int,
    val usesAuth: Boolean
) {
    MASTER_DATA(
        route = MasterData.route,
        iconRes = master_data,
        title = str_biz_master_data,
        description = str_biz_master_data_desc,
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