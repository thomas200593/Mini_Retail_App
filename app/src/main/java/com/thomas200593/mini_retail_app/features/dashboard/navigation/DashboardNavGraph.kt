package com.thomas200593.mini_retail_app.features.dashboard.navigation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.thomas200593.mini_retail_app.R
import com.thomas200593.mini_retail_app.app.navigation.NavigationGraphs.G_DASHBOARD
import com.thomas200593.mini_retail_app.app.navigation.ScreenGraphs
import com.thomas200593.mini_retail_app.core.ui.common.Icons
import com.thomas200593.mini_retail_app.core.ui.component.AppBar
import com.thomas200593.mini_retail_app.features.business.navigation.businessNavGraph
import com.thomas200593.mini_retail_app.features.dashboard.ui.DashboardScreen
import com.thomas200593.mini_retail_app.features.report_analysis.navigation.reportAnalysisNavGraph

fun NavGraphBuilder.dashboardNavGraph(
    onSignOut: () -> Unit,
    onNavigateToAppConfig: () -> Unit
){
    navigation(
        route = G_DASHBOARD,
        startDestination = ScreenGraphs.Dashboard.route
    ){
        composable(
            route = ScreenGraphs.Dashboard.route
        ){
            AppBar.ProvideTopAppBarTitle {
                Text(text = stringResource(id = R.string.str_dashboard))
            }
            AppBar.ProvideTopAppBarNavigationIcon {
                Icon(
                    modifier = Modifier.size(ButtonDefaults.IconSize),
                    imageVector = ImageVector.vectorResource(Icons.TopLevelDestinations.dashboard),
                    contentDescription = null
                )
            }
            AppBar.ProvideTopAppBarAction {
                Icon(
                    modifier = Modifier
                        .size(30.dp)
                        .clickable(onClick = { onNavigateToAppConfig() }),
                    imageVector = ImageVector.vectorResource(id = Icons.Setting.settings),
                    contentDescription = null
                )
                Icon(
                    modifier = Modifier
                        .size(30.dp)
                        .clickable(onClick = { onSignOut() }),
                    imageVector = androidx.compose.material.icons.Icons.AutoMirrored.Filled.ExitToApp,
                    contentDescription = null
                )
            }
            DashboardScreen()
        }

        //children graph beyond dashboard
        businessNavGraph()
        reportAnalysisNavGraph()
    }
}

fun NavController.navigateToDashboard(navOptions: NavOptions) {
    this.navigate(
        route = G_DASHBOARD,
        navOptions
    )
}