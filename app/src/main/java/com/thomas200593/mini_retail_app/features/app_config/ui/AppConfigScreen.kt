package com.thomas200593.mini_retail_app.features.app_config.ui

import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.thomas200593.mini_retail_app.R
import com.thomas200593.mini_retail_app.core.ui.common.Icons.Setting.settings
import com.thomas200593.mini_retail_app.core.ui.component.AppBar

@Composable
fun AppConfigScreen(
    viewModel: AppConfigViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit,
    onNavigateToGeneralConfigMenu: () -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.onOpen()
    }

    TopAppBar(
        onNavigateBack = onNavigateBack
    )
    ScreenContent(
        onNavigateToGeneralConfigMenu = onNavigateToGeneralConfigMenu
    )
}

@Composable
private fun TopAppBar(
    onNavigateBack: () -> Unit
) {
    AppBar.ProvideTopAppBarNavigationIcon {
        Icon(
            modifier = Modifier.clickable(onClick = onNavigateBack),
            imageVector = Icons.AutoMirrored.Default.ArrowBack,
            contentDescription = null
        )
    }
    AppBar.ProvideTopAppBarTitle {
        Row(
            modifier = Modifier.padding(start = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Icon(
                modifier = Modifier
                    .sizeIn(maxHeight = ButtonDefaults.IconSize)
                    .padding(end = 4.dp),
                imageVector = ImageVector.vectorResource(id = settings),
                contentDescription = null
            )
            Text(text = stringResource(id = R.string.str_configuration))
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
         *      Font Size Selection
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
        modifier = Modifier.fillMaxWidth().padding(8.dp),
        onClick = { onNavigateToGeneralConfigMenu() },
        shape = MaterialTheme.shapes.medium
    ) { Text(text = "General Configuration") }
}
