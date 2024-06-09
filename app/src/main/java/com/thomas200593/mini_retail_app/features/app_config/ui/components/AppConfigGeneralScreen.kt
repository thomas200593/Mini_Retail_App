package com.thomas200593.mini_retail_app.features.app_config.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.thomas200593.mini_retail_app.R
import com.thomas200593.mini_retail_app.core.design_system.util.RequestState
import com.thomas200593.mini_retail_app.core.ui.common.Shapes
import com.thomas200593.mini_retail_app.core.ui.component.AppBar
import com.thomas200593.mini_retail_app.features.app_config.entity.AppConfigGeneralMenu

@Composable
fun AppConfigGeneralScreen(
    viewModel: AppConfigGeneralViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit,
    onNavigateToMenu: (AppConfigGeneralMenu) -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.onOpen()
    }

    val appConfigGeneralMenuUiState by viewModel.appConfigGeneralMenuUiState

    TopAppBar(
        onNavigateBack = onNavigateBack
    )
    ScreenContent(
        appConfigGeneralMenuUiState = appConfigGeneralMenuUiState,
        onNavigateToMenu = onNavigateToMenu
    )
}

@Composable
private fun TopAppBar(
    onNavigateBack: () -> Unit
){
    AppBar.ProvideTopAppBarNavigationIcon {
        Icon(
            modifier = Modifier.clickable(
                onClick = onNavigateBack
            ),
            imageVector = Icons.AutoMirrored.Default.ArrowBack,
            contentDescription = null
        )
    }
    AppBar.ProvideTopAppBarTitle {
        Row(
            modifier = Modifier.padding(start = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(text = stringResource(id = R.string.str_configuration_general))
        }
    }
}

@Composable
private fun ScreenContent(
    modifier: Modifier = Modifier,
    appConfigGeneralMenuUiState: RequestState<Set<AppConfigGeneralMenu>>,
    onNavigateToMenu: (AppConfigGeneralMenu) -> Unit,
) {
    when(appConfigGeneralMenuUiState){
        RequestState.Idle -> Unit
        RequestState.Loading -> {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Shapes.DotsLoadingAnimation()
            }
        }
        is RequestState.Error -> Unit
        is RequestState.Success -> {
            val generalConfigMenuData = appConfigGeneralMenuUiState.data!!
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(4.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                Text(text = "General Configuration")
                generalConfigMenuData.forEach { generalConfigMenu ->
                    HorizontalDivider()
                    Surface(
                        onClick = { onNavigateToMenu(generalConfigMenu) },
                        shape = MaterialTheme.shapes.medium,
                        modifier = Modifier.padding(4.dp)
                    ) {
                        Column(
                            modifier = modifier
                                .fillMaxWidth()
                                .padding(4.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Row(modifier = modifier.fillMaxWidth(1.0f)) {
                                Icon(
                                    modifier = modifier
                                        .fillMaxWidth(0.2f)
                                        .size(36.dp),
                                    imageVector = ImageVector.vectorResource(id = generalConfigMenu.imageRes),
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Column(modifier = modifier.fillMaxWidth(0.8f)) {
                                    Text(text = stringResource(id = generalConfigMenu.title), fontWeight = FontWeight.Light)
                                }
                            }
                        }
                    }
                }
                HorizontalDivider()
            }
        }
    }
}