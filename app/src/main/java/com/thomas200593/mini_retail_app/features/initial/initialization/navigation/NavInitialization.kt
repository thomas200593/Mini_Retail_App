package com.thomas200593.mini_retail_app.features.initial.initialization.navigation

import androidx.navigation.NavController
import com.thomas200593.mini_retail_app.app.navigation.NavGraph.G_INITIAL
import com.thomas200593.mini_retail_app.app.navigation.ScrGraphs.Initialization

fun NavController.navToInitialization(){
    this.navigate(
        route = Initialization.route
    ){
        launchSingleTop = true; restoreState = true
        popUpTo(G_INITIAL){ inclusive = true }
    }
}