package com.thomas200593.mini_retail_app.app.navigation

import com.livefront.sealedenum.GenSealedEnum
import kotlinx.serialization.Serializable

@Serializable sealed class ScrGraphs(
    val route: String,
    val usesAuth: Boolean,
    val usesTopBar: Boolean
) {
    /**
     * Companion Object
     */
    @GenSealedEnum(generateEnum = true)
    companion object {
        fun screenWithTopAppBar() = ScrGraphs.sealedEnum.values.filter { it.usesTopBar }
        .map { it.route }.toSet()
    }

    /**
     * Initial
     */
    @Serializable data object Initial: ScrGraphs(
        route = "r_initial",
        usesAuth = false,
        usesTopBar = false
    )
    @Serializable data object Initialization: ScrGraphs(
        route = "r_initialization",
        usesAuth = false,
        usesTopBar = false
    )

    /**
     * Onboarding
     */
    @Serializable data object Onboarding: ScrGraphs(
        route = "r_onboarding",
        usesAuth = false,
        usesTopBar = false
    )

    /**
     * Auth
     */
    @Serializable data object Auth: ScrGraphs(
        route = "r_auth",
        usesAuth = false,
        usesTopBar = false
    )

    /**
     * App Config
     */
    @Serializable data object AppConfig: ScrGraphs(
        route = "r_app_config",
        usesAuth = false,
        usesTopBar = true
    )
    //App Config General
    @Serializable data object ConfigGeneral : ScrGraphs(
        route = "r_conf_gen",
        usesAuth = false,
        usesTopBar = true
    )
    @Serializable data object Country : ScrGraphs(
        route = "r_conf_gen_country",
        usesAuth = false,
        usesTopBar = true
    )
    @Serializable data object Currency: ScrGraphs(
        route = "r_conf_gen_currency",
        usesAuth = false,
        usesTopBar = true
    )
    @Serializable data object DynamicColor: ScrGraphs (
        route = "r_conf_gen_dynamic_color",
        usesAuth = false,
        usesTopBar = true
    )
    @Serializable data object FontSize: ScrGraphs(
        route = "r_conf_gen_font_size",
        usesAuth = false,
        usesTopBar = true
    )
    @Serializable data object Language : ScrGraphs(
        route = "r_conf_gen_language",
        usesAuth = false,
        usesTopBar = true
    )
    @Serializable data object Theme: ScrGraphs (
        route = "r_conf_gen_theme",
        usesAuth = false,
        usesTopBar = true
    )
    @Serializable data object Timezone: ScrGraphs(
        route = "r_conf_gen_timezone",
        usesAuth = false,
        usesTopBar = true
    )
    //App Config Data
    @Serializable data object ConfigData: ScrGraphs(
        route = "r_conf_data",
        usesAuth = true,
        usesTopBar = true
    )
    @Serializable data object DataBackup: ScrGraphs(
        route = "r_conf_data_backup",
        usesAuth = true,
        usesTopBar = true
    )

    /**
     * Dashboard
     */
    @Serializable data object Dashboard: ScrGraphs(
        route = "r_dashboard",
        usesAuth = true,
        usesTopBar = true
    )

    /**
     * Business
     */
    @Serializable data object Business: ScrGraphs(
        route = "r_biz",
        usesAuth = true,
        usesTopBar = true
    )
    @Serializable data object MasterData: ScrGraphs(
        route = "r_biz_m_data",
        usesAuth = true,
        usesTopBar = true
    )
    @Serializable data object Supplier: ScrGraphs(
        route = "r_biz_m_data_supplier",
        usesAuth = true,
        usesTopBar = true
    )
    @Serializable data object BizProfile: ScrGraphs(
        route = "r_biz_profile",
        usesAuth = true,
        usesTopBar = true
    )
    @Serializable data object BizProfileAddressesAddUpdate: ScrGraphs(
        route = "r_biz_profile_addresses_add_update",
        usesAuth = true,
        usesTopBar = true
    )

    /**
     * Reporting
     */
    @Serializable data object Reporting: ScrGraphs(
        route = "r_reporting",
        usesAuth = true,
        usesTopBar = true
    )

    /**
     * User Profile
     */
    @Serializable data object UserProfile: ScrGraphs(
        route = "r_user_profile",
        usesAuth = true,
        usesTopBar = false
    )
}