package com.thomas200593.mini_retail_app.features.business.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.thomas200593.mini_retail_app.app.navigation.NavigationGraphs.G_BUSINESS_MASTER_DATA
import com.thomas200593.mini_retail_app.app.navigation.ScreenGraphs
import com.thomas200593.mini_retail_app.features.business.ui.master_data.BusinessMasterDataScreen

fun NavGraphBuilder.navGraphBusinessMasterData() {
    navigation(
        route = G_BUSINESS_MASTER_DATA,
        startDestination = ScreenGraphs.BusinessMasterData.route
    ){
        composable(
            route = ScreenGraphs.BusinessMasterData.route
        ){
            BusinessMasterDataScreen()
        }
    }
}