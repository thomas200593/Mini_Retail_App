package com.thomas200593.mini_retail_app.features.business.biz_m_data.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.thomas200593.mini_retail_app.app.navigation.NavGraph.G_MASTER_DATA
import com.thomas200593.mini_retail_app.app.navigation.ScrGraphs.MasterData
import com.thomas200593.mini_retail_app.features.business.biz_m_data.ui.ScrMasterData
import com.thomas200593.mini_retail_app.features.business.biz_m_data_supplier.navigation.navGraphSupplier

fun NavGraphBuilder.navGraphMasterData() {
    navigation(
        route = G_MASTER_DATA,
        startDestination = MasterData.route
    ){
        composable(
            route = MasterData.route
        ){ ScrMasterData() }

        /**
         * - Supplier
         */
        navGraphSupplier()
//        navGraphBusinessMasterDataCustomer()
    }
}

fun NavController.navToMasterData(
    navOptions: NavOptions?,
    destMasterData: DestMasterData? = null
){
    this.navigate(
        route = destMasterData?.scrGraphs?.route?: G_MASTER_DATA,
        navOptions
    )
}