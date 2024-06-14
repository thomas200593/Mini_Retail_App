package com.thomas200593.mini_retail_app.features.dashboard.ui

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons.Default
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.thomas200593.mini_retail_app.R
import com.thomas200593.mini_retail_app.core.ui.common.Icons
import com.thomas200593.mini_retail_app.core.ui.component.AppBar

@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel = hiltViewModel()
) {
    TopAppBar()
    ScreenContent(
        onSignOut = viewModel::handleSignOut
    )
}

@Composable
private fun TopAppBar() {
    AppBar.ProvideTopAppBarNavigationIcon {
        Icon(
            modifier = Modifier.size(ButtonDefaults.IconSize),
            imageVector = ImageVector.vectorResource(Icons.TopLevelDestinations.dashboard),
            contentDescription = null
        )
    }
    AppBar.ProvideTopAppBarTitle {
        Text(text = stringResource(id = R.string.str_dashboard))
    }
    AppBar.ProvideTopAppBarAction {
        Icon(
            modifier = Modifier.size(ButtonDefaults.IconSize),
            imageVector = Default.Info,
            contentDescription = null
        )
    }
}

@Composable
private fun ScreenContent(
    onSignOut: () -> Unit
) {
    Text(text = "Dashboard Screen")
    Button(
        onClick = {
            onSignOut()
        }
    ) {
        Text(text = "Test Logout")
    }
}