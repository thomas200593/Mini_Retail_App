package com.thomas200593.mini_retail_app.features.business.biz.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.thomas200593.mini_retail_app.app.navigation.NavGraph.G_BUSINESS
import com.thomas200593.mini_retail_app.app.navigation.ScrGraphs.Business
import com.thomas200593.mini_retail_app.features.business.biz.ui.ScrBiz
import com.thomas200593.mini_retail_app.features.business.biz_m_data.navigation.navGraphMasterData
import com.thomas200593.mini_retail_app.features.business.biz_profile.navigation.navGraphBizProfile

fun NavGraphBuilder.navGraphBiz(){
    navigation(
        route = G_BUSINESS,
        startDestination = Business.route
    ){
        /**
         * - Biz Screen
         */
        composable(
            route = Business.route
        ){ ScrBiz() }

        /**
         * - Graph Master Data
         */
        navGraphMasterData()

        /**
         * - Graph Biz Profile
         * */
        navGraphBizProfile()
    }
}

fun NavController.navToBiz(
    navOptions: NavOptions?,
    destBiz: DestBiz? = null
) {
    this.navigate(
        route = destBiz?.route?: G_BUSINESS,
        navOptions
    )
}