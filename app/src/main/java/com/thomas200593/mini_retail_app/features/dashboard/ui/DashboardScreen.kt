package com.thomas200593.mini_retail_app.features.dashboard.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.thomas200593.mini_retail_app.R
import com.thomas200593.mini_retail_app.core.ui.common.Icons
import com.thomas200593.mini_retail_app.core.ui.component.AppBar

@Composable
fun DashboardScreen(
    
){
    TopAppBar()
    ScreenContent()
}

@Composable
private fun TopAppBar() {
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
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier
                    .size(24.dp)
                    .clickable(onClick = { }),
                imageVector = ImageVector.vectorResource(id = Icons.Setting.settings),
                contentDescription = null
            )
            Icon(
                modifier = Modifier
                    .size(24.dp)
                    .clickable(onClick = { }),
                imageVector = androidx.compose.material.icons.Icons.AutoMirrored.Filled.ExitToApp,
                contentDescription = null
            )
        }
    }
}

@Composable
fun ScreenContent() {
    Text(text = "Dashboard Screen")
}