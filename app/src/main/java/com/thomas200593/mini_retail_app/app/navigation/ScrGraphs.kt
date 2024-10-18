package com.thomas200593.mini_retail_app.app.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.navigation.NavDestination
import com.thomas200593.mini_retail_app.R.string
import com.thomas200593.mini_retail_app.core.ui.common.CustomIcons
import kotlinx.serialization.Serializable
import kotlin.reflect.KType
import kotlin.reflect.full.createType
import kotlin.reflect.full.primaryConstructor

@Serializable
sealed class ScrGraphs(
    val route: String,
    @DrawableRes val iconRes: Int? = null,
    @StringRes val title: Int? = null,
    @StringRes val description: Int? = null,
    val usesAuth: Boolean,
    val usesTopBar: Boolean
) {
    /**
     * Companion Object
     */
    companion object {

        fun getByRoute(destCurrent: NavDestination?): ScrGraphs? = ScrGraphs::class.sealedSubclasses
            .mapNotNull { subClass ->
                val instance =
                    subClass.objectInstance ?: subClass.primaryConstructor?.callBy(
                        subClass.primaryConstructor!!.parameters.associateWith { it.type.defaultValue() }
                    )
                instance
            }
            .firstOrNull { it.route == destCurrent?.route }

        fun getScreenRoutesUsesTopBar() = ScrGraphs::class.sealedSubclasses
            .mapNotNull { subclass ->
                val instance =
                    subclass.objectInstance ?: subclass.primaryConstructor?.callBy(
                        subclass.primaryConstructor!!.parameters.associateWith { it.type.defaultValue() }
                    )
                instance?.takeIf { it.usesTopBar }?.route
            }.toSet()
    }

    /**
     * Initial
     */
    @Serializable
    data object Initial : ScrGraphs(
        route = "r_initial",
        usesAuth = false,
        usesTopBar = false
    )

    @Serializable
    data object Initialization : ScrGraphs(
        route = "r_initialization",
        usesAuth = false,
        usesTopBar = false
    )

    /**
     * Onboarding
     */
    @Serializable
    data object Onboarding : ScrGraphs(
        route = "r_onboarding",
        title = string.str_onboarding,
        description = string.str_onboarding,
        usesAuth = false,
        usesTopBar = false
    )

    /**
     * Auth
     */
    @Serializable
    data object Auth : ScrGraphs(
        route = "r_auth",
        title = string.str_auth,
        usesAuth = false,
        usesTopBar = false
    )

    /**
     * App Config
     */
    @Serializable
    data object AppConfig : ScrGraphs(
        route = "r_app_config",
        iconRes = CustomIcons.Setting.settings,
        title = string.str_configuration,
        description = string.str_configuration,
        usesAuth = false,
        usesTopBar = true
    )

    //App Config General
    @Serializable
    data object AppConfigGeneral : ScrGraphs(
        route = "r_conf_gen",
        iconRes = CustomIcons.Setting.settings_general,
        title = string.str_configuration_general,
        description = string.str_configuration_general,
        usesAuth = false,
        usesTopBar = true
    )

    @Serializable
    data object ConfGenCountry : ScrGraphs(
        route = "r_conf_gen_country",
        iconRes = CustomIcons.Country.country,
        title = string.str_country,
        description = string.str_country_desc,
        usesAuth = false,
        usesTopBar = true
    )

    @Serializable
    data object ConfGenCurrency : ScrGraphs(
        route = "r_conf_gen_currency",
        iconRes = CustomIcons.Currency.currency,
        title = string.str_currency,
        description = string.str_currency_desc,
        usesAuth = false,
        usesTopBar = true
    )

    @Serializable
    data object ConfGenDynamicColor : ScrGraphs(
        route = "r_conf_gen_dynamic_color",
        iconRes = CustomIcons.DynamicColor.dynamic_color,
        title = string.str_dynamic_color,
        description = string.str_dynamic_color_desc,
        usesAuth = false,
        usesTopBar = true
    )

    @Serializable
    data object ConfGenFontSize : ScrGraphs(
        route = "r_conf_gen_font_size",
        iconRes = CustomIcons.Font.font,
        title = string.str_size_font,
        description = string.str_size_font_desc,
        usesAuth = false,
        usesTopBar = true
    )

    @Serializable
    data object ConfGenLanguage : ScrGraphs(
        route = "r_conf_gen_language",
        iconRes = CustomIcons.Language.language,
        title = string.str_lang,
        description = string.str_lang_desc,
        usesAuth = false,
        usesTopBar = true
    )

    @Serializable
    data object ConfGenTheme : ScrGraphs(
        route = "r_conf_gen_theme",
        iconRes = CustomIcons.Theme.theme,
        title = string.str_theme,
        description = string.str_theme_desc,
        usesAuth = false,
        usesTopBar = true
    )

    @Serializable
    data object ConfGenTimezone : ScrGraphs(
        route = "r_conf_gen_timezone",
        iconRes = CustomIcons.Timezone.timezone,
        title = string.str_timezone,
        description = string.str_timezone,
        usesAuth = false,
        usesTopBar = true
    )

    //App Config Data
    @Serializable
    data object AppConfigData : ScrGraphs(
        route = "r_conf_data",
        iconRes = CustomIcons.Setting.settings_data,
        title = string.str_configuration_data,
        description = string.str_configuration_data,
        usesAuth = true,
        usesTopBar = true
    )

    @Serializable
    data object DataBackup : ScrGraphs(
        route = "r_conf_data_backup",
        iconRes = CustomIcons.Data.backup,
        title = string.str_backup,
        description = string.str_backup_desc,
        usesAuth = true,
        usesTopBar = true
    )

    /**
     * Dashboard
     */
    @Serializable
    data object Dashboard : ScrGraphs(
        route = "r_dashboard",
        iconRes = CustomIcons.TopLevelDestinations.dashboard,
        title = string.str_dashboard,
        description = string.str_dashboard,
        usesAuth = true,
        usesTopBar = true
    )

    /**
     * Business
     */
    @Serializable
    data object Business : ScrGraphs(
        route = "r_biz",
        iconRes = CustomIcons.TopLevelDestinations.business,
        title = string.str_business,
        description = string.str_business,
        usesAuth = true,
        usesTopBar = true
    )

    @Serializable
    data object MasterData : ScrGraphs(
        route = "r_biz_m_data",
        iconRes = CustomIcons.Data.master_data,
        title = string.str_biz_master_data,
        description = string.str_biz_master_data_desc,
        usesAuth = true,
        usesTopBar = true
    )

    @Serializable
    data object Supplier : ScrGraphs(
        route = "r_biz_m_data_supplier",
        iconRes = CustomIcons.Supplier.supplier,
        title = string.str_supplier,
        description = string.str_supplier_desc,
        usesAuth = true,
        usesTopBar = true
    )

    @Serializable
    data object BizProfile : ScrGraphs(
        route = "r_biz_profile",
        iconRes = CustomIcons.Business.business_profile,
        title = string.str_business_profile,
        description = string.str_business_profile_desc,
        usesAuth = true,
        usesTopBar = true
    )

    @Serializable
    data object BizProfileAddressesAddUpdate : ScrGraphs(
        route = "r_biz_profile_addresses_add_update",
        usesAuth = true,
        usesTopBar = true
    )

    /**
     * Reporting
     */
    @Serializable
    data object Reporting : ScrGraphs(
        route = "r_reporting",
        iconRes = CustomIcons.TopLevelDestinations.reporting,
        title = string.str_reporting,
        description = string.str_reporting,
        usesAuth = true,
        usesTopBar = true
    )

    /**
     * User Profile
     */
    @Serializable
    data object UserProfile : ScrGraphs(
        route = "r_user_profile",
        iconRes = CustomIcons.TopLevelDestinations.user_profile,
        title = string.str_user_profile,
        description = string.str_user_profile,
        usesAuth = true,
        usesTopBar = true
    )
}

fun KType.defaultValue(): Any? = when (this) {
    String::class.createType() -> String()
    Int::class.createType() -> 0
    Boolean::class.createType() -> false
    Double::class.createType() -> 0.0
    else -> null
}