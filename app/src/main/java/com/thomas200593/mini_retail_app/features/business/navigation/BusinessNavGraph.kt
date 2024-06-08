package com.thomas200593.mini_retail_app.features.business.navigation

import androidx.compose.material3.Text
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.thomas200593.mini_retail_app.R
import com.thomas200593.mini_retail_app.features.business.ui.BusinessScreen
import com.thomas200593.mini_retail_app.app.navigation.NavigationGraphs.G_BUSINESS
import com.thomas200593.mini_retail_app.app.navigation.ScreenGraphs
import com.thomas200593.mini_retail_app.core.ui.component.AppBar

fun NavGraphBuilder.businessNavGraph(){
    navigation(
        route = G_BUSINESS,
        startDestination = ScreenGraphs.Business.route
    ){
        composable(
            route = ScreenGraphs.Business.route
        ){
            AppBar.ProvideTopAppBarTitle {
                Text(text = stringResource(id = R.string.str_business))
            }
            BusinessScreen()
        }
    }
}

fun NavController.navigateToBusiness(navOptions: NavOptions?) {
    this.navigate(
        route = G_BUSINESS,
        navOptions
    )
}