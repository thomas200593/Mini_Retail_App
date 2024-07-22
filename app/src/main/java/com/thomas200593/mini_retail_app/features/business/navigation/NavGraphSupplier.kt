package com.thomas200593.mini_retail_app.features.business.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.thomas200593.mini_retail_app.app.navigation.NavGraph
import com.thomas200593.mini_retail_app.app.navigation.ScrGraphs
import com.thomas200593.mini_retail_app.features.business.ui.master_data.supplier.list.SupplierListScreen

fun NavGraphBuilder.navGraphSupplier(){
    navigation(
        route = NavGraph.G_SUPPLIER,
        startDestination = ScrGraphs.Supplier.route
    ){
        composable(
            route = ScrGraphs.Supplier.route
        ){
            SupplierListScreen()
        }
    }
}