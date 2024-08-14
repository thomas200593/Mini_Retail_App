package com.thomas200593.mini_retail_app.features.business.biz_profile.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.thomas200593.mini_retail_app.R.string
import com.thomas200593.mini_retail_app.app.navigation.ScrGraphs.BizProfileAddressesAddUpdate
import com.thomas200593.mini_retail_app.app.navigation.ScrGraphs.BizProfileContactsAddUpdate
import com.thomas200593.mini_retail_app.app.navigation.ScrGraphs.BizProfileLinksAddUpdate
import com.thomas200593.mini_retail_app.core.ui.common.CustomIcons.Country.country

enum class DestBizProfile(
    val route: String,
    @DrawableRes val iconRes: Int,
    @StringRes val title: Int,
    @StringRes val description: Int,
    val usesAuth: Boolean
){
    BIZ_PROFILE_ADDRESSES(
        route = BizProfileAddressesAddUpdate.route,
        iconRes = country,
        title = string.str_biz_addresses,
        description = string.str_biz_addresses,
        usesAuth = true
    ),
    BIZ_PROFILE_CONTACTS(
        route = BizProfileContactsAddUpdate.route,
        iconRes = country,
        title = string.str_biz_contacts,
        description = string.str_biz_contacts,
        usesAuth = true
    ),
    BIZ_PROFILE_LINKS(
        route = BizProfileLinksAddUpdate.route,
        iconRes = country,
        title = string.str_biz_links,
        description = string.str_biz_links,
        usesAuth = true
    )
}