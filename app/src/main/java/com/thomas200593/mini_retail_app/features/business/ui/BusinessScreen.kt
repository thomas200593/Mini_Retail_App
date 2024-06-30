package com.thomas200593.mini_retail_app.features.business.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.navOptions
import com.thomas200593.mini_retail_app.R
import com.thomas200593.mini_retail_app.app.ui.AppState
import com.thomas200593.mini_retail_app.app.ui.LocalAppState
import com.thomas200593.mini_retail_app.core.data.local.session.SessionState
import com.thomas200593.mini_retail_app.core.design_system.util.RequestState
import com.thomas200593.mini_retail_app.core.ui.common.Icons
import com.thomas200593.mini_retail_app.core.ui.component.AppBar
import com.thomas200593.mini_retail_app.core.ui.component.CommonMessagePanel.EmptyScreen
import com.thomas200593.mini_retail_app.core.ui.component.CommonMessagePanel.ErrorScreen
import com.thomas200593.mini_retail_app.core.ui.component.CommonMessagePanel.LoadingScreen
import com.thomas200593.mini_retail_app.features.business.navigation.DestinationBusiness
import com.thomas200593.mini_retail_app.features.business.navigation.navigateToBusiness
import timber.log.Timber

private const val TAG = "BusinessScreen"

@Composable
fun BusinessScreen(
    viewModel: BusinessViewModel = hiltViewModel(),
    appState: AppState = LocalAppState.current
){
    Timber.d("Called : fun $TAG()")
    val sessionState by appState.isSessionValid.collectAsStateWithLifecycle()
    val businessMenuPreferences by viewModel.businessMenuPreferences

    when(sessionState){
        SessionState.Loading -> {
            LoadingScreen()
        }
        is SessionState.Invalid -> {
            LaunchedEffect(Unit) {
                viewModel.onOpen(sessionState)
            }
        }
        is SessionState.Valid -> {
            LaunchedEffect(Unit) {
                viewModel.onOpen(sessionState)
            }
        }
    }

    TopAppBar()
    ScreenContent(
        businessMenuPreferences = businessMenuPreferences,
        onNavigateToMenu = { menu ->
            appState.navController.navigateToBusiness(
                navOptions = navOptions {
                    launchSingleTop = true
                    restoreState = true
                },
                destinationBusiness = menu
            )
        }
    )
}

@Composable
private fun TopAppBar() {
    AppBar.ProvideTopAppBarTitle {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ){
            Icon(
                modifier = Modifier
                    .sizeIn(maxHeight = ButtonDefaults.IconSize),
                imageVector = ImageVector.vectorResource(id = Icons.TopLevelDestinations.business),
                contentDescription = null
            )
            Text(
                text = stringResource(id = R.string.str_business),
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
                imageVector = androidx.compose.material.icons.Icons.Default.Info,
                contentDescription = null
            )
        }
    }
}

@Composable
private fun ScreenContent(
    businessMenuPreferences: RequestState<Set<DestinationBusiness>>,
    onNavigateToMenu: (DestinationBusiness) -> Unit
) {
    when(businessMenuPreferences){
        RequestState.Idle, RequestState.Loading -> {
            LoadingScreen()
        }
        is RequestState.Error -> {
            ErrorScreen(
                title = stringResource(id = R.string.str_error),
                errorMessage = stringResource(id = R.string.str_error_fetching_preferences),
                showIcon = true
            )
        }
        RequestState.Empty -> {
            EmptyScreen(
                title = stringResource(id = R.string.str_empty_message_title),
                emptyMessage = stringResource(id = R.string.str_empty_message),
                showIcon = true
            )
        }
        is RequestState.Success -> {
            val menuPreferences = businessMenuPreferences.data
            LazyVerticalGrid(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                columns = GridCells.Fixed(3)
            ) {
                items(count = menuPreferences.count()){ index ->
                    val menu = menuPreferences.elementAt(index)
                    Surface(
                        modifier = Modifier.fillMaxWidth()
                            .padding(4.dp),
                        onClick = { onNavigateToMenu(menu) },
                        shape = MaterialTheme.shapes.medium,
                        border = BorderStroke(1.dp, Color.DarkGray),
                    ) {
                        Column(
                            Modifier
                                .fillMaxSize()
                                .padding(8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterVertically)
                        ) {
                            Surface(
                                shape = MaterialTheme.shapes.medium,
                                color = MaterialTheme.colorScheme.tertiaryContainer,
                            ) {
                                Icon(
                                    modifier = Modifier.padding(8.dp),
                                    imageVector = ImageVector.vectorResource(id = menu.iconRes),
                                    contentDescription = null
                                )
                            }
                            Text(
                                text = stringResource(id = menu.title),
                                modifier = Modifier.fillMaxWidth(),
                                color = MaterialTheme.colorScheme.onTertiaryContainer,
                                maxLines = 2,
                                textAlign = TextAlign.Center,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                }
            }
        }
    }
}