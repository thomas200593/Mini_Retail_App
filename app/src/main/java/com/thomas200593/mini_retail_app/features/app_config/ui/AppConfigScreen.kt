package com.thomas200593.mini_retail_app.features.app_config.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.thomas200593.mini_retail_app.R
import com.thomas200593.mini_retail_app.app.ui.AppState
import com.thomas200593.mini_retail_app.app.ui.LocalAppState
import com.thomas200593.mini_retail_app.core.ui.common.Icons.Setting.settings
import com.thomas200593.mini_retail_app.core.ui.component.AppBar
import com.thomas200593.mini_retail_app.features.app_config.navigation.navigateToAppConfigGeneral
import timber.log.Timber

private const val TAG = "AppConfigScreen"

@Composable
fun AppConfigScreen(
    viewModel: AppConfigViewModel = hiltViewModel(),
    appState: AppState = LocalAppState.current
) {
    Timber.d("Called: %s", TAG)

    LaunchedEffect(Unit) {
        viewModel.onOpen()
    }

    TopAppBar(
        onNavigateBack = appState::onNavigateUp
    )
    ScreenContent(
        onNavigateToGeneralConfigMenu = {
            appState.navController.navigateToAppConfigGeneral(null)
        }
    )
}

@Composable
private fun TopAppBar(
    onNavigateBack: () -> Unit
) {
    AppBar.ProvideTopAppBarNavigationIcon {
        Surface(
            onClick = onNavigateBack,
            modifier = Modifier
        ) {
            Icon(
                modifier = Modifier,
                imageVector = Icons.AutoMirrored.Default.KeyboardArrowLeft,
                contentDescription = null
            )
        }
    }
    AppBar.ProvideTopAppBarTitle {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ){
            Icon(
                modifier = Modifier
                    .sizeIn(maxHeight = ButtonDefaults.IconSize),
                imageVector = ImageVector.vectorResource(id = settings),
                contentDescription = null
            )
            Text(
                text = stringResource(id = R.string.str_configuration),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
    AppBar.ProvideTopAppBarAction {
        Row(
            modifier = Modifier.padding(end = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ){
            Icon(
                modifier = Modifier
                    .sizeIn(maxHeight = ButtonDefaults.IconSize),
                imageVector = Icons.Default.Info,
                contentDescription = null
            )
        }
    }
}

@Composable
private fun ScreenContent(
    modifier: Modifier = Modifier,
    onNavigateToGeneralConfigMenu: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        /**
         * General Config
         *      Theme Selection V
         *      Dynamic Color Selection V
         *      Language Selection V
         *      Timezone Selection V
         *      Default Currencies Selection V
         *      Font Size Selection V
         */
        GeneralConfig(onNavigateToGeneralConfigMenu = onNavigateToGeneralConfigMenu)

        /**
         * Data Setting
         *      Daily Backup
         *          Turn on / off, at what time?
         *          Default Path
         *          What to Backup
         *      Master Data
         *          Import Master Data
         */
//        DataConfig()

        /**
         * Security Related Settings (Need Log in)
         *      Permissions
         *      Connected Peripherals
         *      Notifications
         */
//        SecurityConfig()

        /**
         * About Application
         *      App Version
         *      Terms and Conditions
         *      Privacy Policy
         *      Open Source License
         *      Contact Developers [Google Form API]
         *      Clear Cache
         */
//        AboutApplication()
    }
}

@Composable
private fun GeneralConfig(onNavigateToGeneralConfigMenu:() -> Unit) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        onClick = { onNavigateToGeneralConfigMenu() },
        shape = MaterialTheme.shapes.medium
    ) { 
        Text(
            text = stringResource(id = R.string.str_configuration_general),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        ) 
    }
}
