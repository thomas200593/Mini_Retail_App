package com.thomas200593.mini_retail_app.app.navigation

import com.thomas200593.mini_retail_app.app.navigation.ScrGraphs.AppConfig
import com.thomas200593.mini_retail_app.app.navigation.ScrGraphs.BizProfile
import com.thomas200593.mini_retail_app.app.navigation.ScrGraphs.BizProfileAddressesAddUpdate
import com.thomas200593.mini_retail_app.app.navigation.ScrGraphs.BizProfileContactsAddUpdate
import com.thomas200593.mini_retail_app.app.navigation.ScrGraphs.BizProfileLinksAddUpdate
import com.thomas200593.mini_retail_app.app.navigation.ScrGraphs.Business
import com.thomas200593.mini_retail_app.app.navigation.ScrGraphs.ConfigData
import com.thomas200593.mini_retail_app.app.navigation.ScrGraphs.ConfigGeneral
import com.thomas200593.mini_retail_app.app.navigation.ScrGraphs.Country
import com.thomas200593.mini_retail_app.app.navigation.ScrGraphs.Currency
import com.thomas200593.mini_retail_app.app.navigation.ScrGraphs.Customer
import com.thomas200593.mini_retail_app.app.navigation.ScrGraphs.Dashboard
import com.thomas200593.mini_retail_app.app.navigation.ScrGraphs.DynamicColor
import com.thomas200593.mini_retail_app.app.navigation.ScrGraphs.FontSize
import com.thomas200593.mini_retail_app.app.navigation.ScrGraphs.Language
import com.thomas200593.mini_retail_app.app.navigation.ScrGraphs.MasterData
import com.thomas200593.mini_retail_app.app.navigation.ScrGraphs.Reporting
import com.thomas200593.mini_retail_app.app.navigation.ScrGraphs.Supplier
import com.thomas200593.mini_retail_app.app.navigation.ScrGraphs.Theme
import com.thomas200593.mini_retail_app.app.navigation.ScrGraphs.Timezone

object DestWithTopAppBar{
    fun destWithTopAppBar(): Set<String> {
        return setOf(
            /**
             * App Config
             */
            AppConfig.route,
            //Config General
            ConfigGeneral.route,
            Country.route,
            Currency.route,
            DynamicColor.route,
            FontSize.route,
            Language.route,
            Theme.route,
            Timezone.route,
            //Config Data
            ConfigData.route,

            /**
             * Dashboard
             */
            Dashboard.route,

            /**
             * Business
             */
            Business.route,
            //Master Data
            MasterData.route,
            Customer.route,
            Supplier.route,
            //Biz Profile
            BizProfile.route,
            BizProfileAddressesAddUpdate.route,
            BizProfileContactsAddUpdate.route,
            BizProfileLinksAddUpdate.route,

            /**
             * Reporting
             */
            Reporting.route,
        )
    }
}