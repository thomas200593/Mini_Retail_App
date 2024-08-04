package com.thomas200593.mini_retail_app.features.business.biz_m_data_supplier.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.thomas200593.mini_retail_app.app.navigation.NavGraph.G_SUPPLIER
import com.thomas200593.mini_retail_app.app.navigation.ScrGraphs.Supplier
import com.thomas200593.mini_retail_app.features.business.biz_m_data_supplier.ui.list.SupplierListScreen

fun NavGraphBuilder.navGraphSupplier(){
    navigation(
        route = G_SUPPLIER,
        startDestination = Supplier.route
    ){
        composable(
            route = Supplier.route
        ){ SupplierListScreen() }
    }
}