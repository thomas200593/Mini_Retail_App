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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.thomas200593.mini_retail_app.core.data.local.session.SessionState
import com.thomas200593.mini_retail_app.core.ui.common.Icons
import com.thomas200593.mini_retail_app.core.ui.component.AppBar
import com.thomas200593.mini_retail_app.core.ui.component.SessionHandler.SessionHandler
import timber.log.Timber

private const val TAG = "DashboardScreen"

@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel = hiltViewModel(),
    appState: AppState = LocalAppState.current
) {
    Timber.d("Called: %s", TAG)

    var sessionAppState: SessionState by remember {
        mutableStateOf(SessionState.Loading)
    }

    SessionHandler(
        sessionState = appState.isCurrentSessionValid,
        onLoading = {
            sessionAppState = SessionState.Loading
        },
        onInvalid = { throwable, reason ->
            sessionAppState = SessionState.Invalid(throwable, reason)
        },
        onValid = { userData ->
            sessionAppState = userData?.let { SessionState.Valid(it) }!!
        }
    )
    TopAppBar()
    ScreenContent(
        onSignOut = {
            viewModel.handleSignOut()
        },
        sessionAppState = sessionAppState
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
                imageVector = ImageVector.vectorResource(id = Icons.TopLevelDestinations.dashboard),
                contentDescription = null
            )
            Text(
                text = stringResource(id = R.string.str_dashboard),
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
                imageVector = Default.Info,
                contentDescription = null
            )
        }
    }
}

@Composable
private fun ScreenContent(
    onSignOut: () -> Unit,
    sessionAppState: SessionState,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Dashboard Screen")
        Button(onClick = {
            onSignOut.invoke()
        }) {
            Text(text = "Logout")
        }
        Text(text = "Session App State: $sessionAppState")
    }
}