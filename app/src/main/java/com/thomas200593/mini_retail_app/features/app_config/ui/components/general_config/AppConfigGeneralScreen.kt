package com.thomas200593.mini_retail_app.features.app_config.ui.components.general_config

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.thomas200593.mini_retail_app.R
import com.thomas200593.mini_retail_app.core.design_system.util.RequestState
import com.thomas200593.mini_retail_app.core.ui.common.Icons.Setting.settings_general
import com.thomas200593.mini_retail_app.core.ui.common.Shapes
import com.thomas200593.mini_retail_app.core.ui.component.AppBar
import com.thomas200593.mini_retail_app.features.app_config.navigation.ConfigGeneralDestination

@Composable
fun AppConfigGeneralScreen(
    viewModel: AppConfigGeneralViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit,
    onNavigateToMenu: (ConfigGeneralDestination) -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.onOpen()
    }

    val generalMenuPreferences by viewModel.generalMenuPreferences

    TopAppBar(
        onNavigateBack = onNavigateBack
    )
    ScreenContent(
        generalMenuPreferences = generalMenuPreferences,
        onNavigateToMenu = onNavigateToMenu
    )
}

@Composable
private fun TopAppBar(
    onNavigateBack: () -> Unit
){
    AppBar.ProvideTopAppBarNavigationIcon {
        Surface(
            onClick =  onNavigateBack,
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
                imageVector = ImageVector.vectorResource(id = settings_general),
                contentDescription = null
            )
            Text(
                text = stringResource(id = R.string.str_configuration_general),
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
    generalMenuPreferences: RequestState<Set<ConfigGeneralDestination>>,
    onNavigateToMenu: (ConfigGeneralDestination) -> Unit
) {
    when(generalMenuPreferences){
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
                Text(
                    text = stringResource(id = R.string.str_loading),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
        is RequestState.Error -> {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(id = R.string.str_error),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
        is RequestState.Success -> {
            val appConfigGeneralMenuPreferences = generalMenuPreferences.data ?: emptySet()

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(count = appConfigGeneralMenuPreferences.count()){ index ->
                    val menu = appConfigGeneralMenuPreferences.elementAt(index)
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth(1.0f),
                        shape = MaterialTheme.shapes.medium,
                        border = BorderStroke(width = 1.dp, color = Color(0xFF747775)),
                        onClick = { onNavigateToMenu(menu) }
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(1.0f)
                                .padding(8.dp)
                                .height(intrinsicSize = IntrinsicSize.Max),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(modifier = Modifier.weight(0.2f)) {
                                Icon(
                                    imageVector = ImageVector.vectorResource(menu.iconRes),
                                    contentDescription = null
                                )
                            }
                            Column(
                                modifier = Modifier.weight(0.8f),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Text(
                                    text = stringResource(id = menu.title),
                                    modifier = Modifier.fillMaxWidth(),
                                    textAlign = TextAlign.Start,
                                    fontWeight = FontWeight.Bold,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Text(
                                    text = stringResource(id = menu.description),
                                    modifier = Modifier.fillMaxWidth(),
                                    textAlign = TextAlign.Start,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}