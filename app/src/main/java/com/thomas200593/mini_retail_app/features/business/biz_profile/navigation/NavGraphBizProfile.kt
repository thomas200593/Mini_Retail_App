package com.thomas200593.mini_retail_app.features.business.biz_profile.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType.Companion.StringType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.thomas200593.mini_retail_app.app.navigation.NavGraph.G_BIZ_PROFILE
import com.thomas200593.mini_retail_app.app.navigation.ScrGraphs.BizProfile
import com.thomas200593.mini_retail_app.app.navigation.ScrGraphs.BizProfileAddressesAddUpdate
import com.thomas200593.mini_retail_app.core.data.local.database.entity_common.Address
import com.thomas200593.mini_retail_app.features.business.biz_profile.ui.ScrBizProfile
import com.thomas200593.mini_retail_app.features.business.biz_profile_address.ui.ScrBizAddressAddUpdate

fun NavGraphBuilder.navGraphBizProfile(){
    navigation(
        route = G_BIZ_PROFILE,
        startDestination = BizProfile.route
    ){
        composable(
            route = BizProfile.route
        ){ ScrBizProfile() }

        composable(
            route = BizProfileAddressesAddUpdate.route + "?genId={genId}",
            arguments = listOf(
                navArgument(name = "genId") { type = StringType; defaultValue = null }
            )
        ) {
            ScrBizAddressAddUpdate(genId = it.arguments?.getString("genId"))
        }
    }
}

fun NavController.navToBizProfileAddressesAddUpdate(address: Address? = null){
    this.navigate(
        route = BizProfileAddressesAddUpdate.route + "?genId=${address?.genId}"
    )
}