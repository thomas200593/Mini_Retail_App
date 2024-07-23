package com.thomas200593.mini_retail_app.features.dashboard.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons.Default
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.thomas200593.mini_retail_app.R
import com.thomas200593.mini_retail_app.app.ui.StateApp
import com.thomas200593.mini_retail_app.app.ui.LocalStateApp
import com.thomas200593.mini_retail_app.core.data.local.session.SessionState
import com.thomas200593.mini_retail_app.core.ui.common.CustomIcons
import com.thomas200593.mini_retail_app.core.ui.component.CustomAppBar
import com.thomas200593.mini_retail_app.core.ui.component.CustomPanel.LoadingScreen
import com.thomas200593.mini_retail_app.features.initial.initial.navigation.navToInitial
import timber.log.Timber

private const val TAG = "DashboardScreen"

@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel = hiltViewModel(),
    stateApp: StateApp = LocalStateApp.current
) {
    Timber.d("Called : fun $TAG()")

    val sessionState by stateApp.isSessionValid.collectAsStateWithLifecycle()

    when(sessionState){
        is SessionState.Invalid -> {
            LaunchedEffect(key1 = Unit) {
                stateApp.navController.navToInitial()
            }
        }
        SessionState.Loading -> {
            LoadingScreen()
        }
        is SessionState.Valid -> {
            LaunchedEffect(Unit) {
                viewModel.onOpen()
            }
        }
    }

    TopAppBar()
    ScreenContent()
}

@Composable
private fun TopAppBar() {
    CustomAppBar.ProvideTopAppBarTitle {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ){
            Icon(
                modifier = Modifier
                    .sizeIn(maxHeight = ButtonDefaults.IconSize),
                imageVector = ImageVector.vectorResource(id = CustomIcons.TopLevelDestinations.dashboard),
                contentDescription = null
            )
            Text(
                text = stringResource(id = R.string.str_dashboard),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
    CustomAppBar.ProvideTopAppBarAction {
        Row(
            modifier = Modifier.padding(end = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ){
            Icon(
                modifier = Modifier
                    .sizeIn(maxHeight = ButtonDefaults.IconSize),
                imageVector = Default.Info,
                contentDescription = null
            )
        }
    }
}

@Composable
private fun ScreenContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //Show Company Profile Card
        //Show Summary
        //Show Notifications & Background job if any
    }
}