package com.thomas200593.mini_retail_app.features.reporting.ui

import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import com.thomas200593.mini_retail_app.R
import com.thomas200593.mini_retail_app.core.ui.common.Icons
import com.thomas200593.mini_retail_app.core.ui.component.AppBar

@Composable
fun ReportingScreen(){
    TopAppBar()
    ScreenContent()
}

@Composable
private fun TopAppBar() {
    AppBar.ProvideTopAppBarNavigationIcon {
        Icon(
            modifier = Modifier.size(ButtonDefaults.IconSize),
            imageVector = ImageVector.vectorResource(Icons.TopLevelDestinations.reporting),
            contentDescription = null
        )
    }
    AppBar.ProvideTopAppBarTitle {
        Text(text = stringResource(id = R.string.str_reporting))
    }
}

@Composable
private fun ScreenContent() {
    Text(text = "Report and Analysis")
}
