package com.thomas200593.mini_retail_app.features.business.biz_profile.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons.AutoMirrored.Filled
import androidx.compose.material.icons.Icons.Default
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.ButtonDefaults.IconSize
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.shapes
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
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextAlign.Companion.Start
import androidx.compose.ui.text.style.TextOverflow.Companion.Ellipsis
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.thomas200593.mini_retail_app.R.string.str_business_profile
import com.thomas200593.mini_retail_app.app.ui.LocalStateApp
import com.thomas200593.mini_retail_app.app.ui.StateApp
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState.Empty
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState.Error
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState.Idle
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState.Loading
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState.Success
import com.thomas200593.mini_retail_app.core.ui.common.CustomIcons.Country.country
import com.thomas200593.mini_retail_app.core.ui.common.CustomThemes
import com.thomas200593.mini_retail_app.core.ui.component.CustomAppBar.ProvideTopAppBarAction
import com.thomas200593.mini_retail_app.core.ui.component.CustomAppBar.ProvideTopAppBarNavigationIcon
import com.thomas200593.mini_retail_app.core.ui.component.CustomAppBar.ProvideTopAppBarTitle
import com.thomas200593.mini_retail_app.core.ui.component.CustomPanel.ErrorScreen
import com.thomas200593.mini_retail_app.core.ui.component.CustomPanel.LoadingScreen
import com.thomas200593.mini_retail_app.features.app_conf.app_config.entity.AppConfig
import com.thomas200593.mini_retail_app.features.business.biz_profile.entity.BizProfile
import com.thomas200593.mini_retail_app.features.business.biz_profile.entity.BizProfileDtl

@Composable
fun ScrBizProfile(
    vm: VMBizProfile = hiltViewModel(),
    stateApp: StateApp = LocalStateApp.current
){
    val sessionState by stateApp.isSessionValid.collectAsStateWithLifecycle()
    val uiState by vm.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(sessionState) { vm.onEvent(VMBizProfile.UiEvents.OnOpenEvents(sessionState)) }
    TopAppBar(onNavigateBack = {  })
    when(uiState.bizProfile){
        Idle, Loading -> LoadingScreen()
        is Error -> ErrorScreen()
        Empty -> ScreenEmptyContent()
        is Success -> ScreenContent(
            bizProfileDtl = (uiState.bizProfile as Success).data
        )
    }
}

@Composable
fun TopAppBar(onNavigateBack: () -> Unit) {
    ProvideTopAppBarNavigationIcon {
        Surface(onClick = onNavigateBack, modifier = Modifier) {
            Icon(
                modifier = Modifier,
                imageVector = Filled.KeyboardArrowLeft,
                contentDescription = null
            )
        }
    }
    ProvideTopAppBarTitle {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ){
            Icon(
                modifier = Modifier.sizeIn(maxHeight = IconSize),
                imageVector = ImageVector.vectorResource(id = country),
                contentDescription = null
            )
            Text(
                text = stringResource(id = str_business_profile),
                maxLines = 1,
                overflow = Ellipsis
            )
        }
    }
    ProvideTopAppBarAction {
        Row(
            modifier = Modifier.padding(end = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ){
            Icon(
                modifier = Modifier.sizeIn(maxHeight = IconSize),
                imageVector = Default.Info,
                contentDescription = null
            )
        }
    }
}

@Composable
private fun ScreenEmptyContent() {

}

@Composable
private fun ScreenContent(
    bizProfileDtl: BizProfileDtl
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.Top)
    ) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = shapes.medium
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ){
                Row(
                    modifier = Modifier.fillMaxWidth(1.0f),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(modifier = Modifier.weight(0.1f)) {
                        Icon(
                            modifier = Modifier.size(20.dp),
                            imageVector = ImageVector.vectorResource(id = country),
                            contentDescription = null
                        )
                    }
                    Text(
                        text = "Business Identity",
                        fontWeight = Bold,
                        textAlign = Start,
                        overflow = Ellipsis,
                        maxLines = 1,
                        modifier = Modifier
                            .weight(0.7f)
                            .fillMaxWidth()
                    )
                    Row(
                        modifier = Modifier.weight(0.2f),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(modifier = Modifier.weight(0.1f)) {
                            Icon(
                                modifier = Modifier.size(20.dp),
                                imageVector = Default.Edit,
                                contentDescription = null
                            )
                        }
                        Surface(modifier = Modifier.weight(0.1f)) {
                            Icon(
                                modifier = Modifier.size(20.dp),
                                imageVector = Default.Clear,
                                contentDescription = null
                            )
                        }
                    }
                }
            }
        }

        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = shapes.medium
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ){
                Row(
                    modifier = Modifier.fillMaxWidth(1.0f),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(modifier = Modifier.weight(0.1f)) {
                        Icon(
                            modifier = Modifier.size(20.dp),
                            imageVector = ImageVector.vectorResource(id = country),
                            contentDescription = null
                        )
                    }
                    Text(
                        text = "Business Addresses",
                        fontWeight = Bold,
                        textAlign = Start,
                        overflow = Ellipsis,
                        maxLines = 1,
                        modifier = Modifier
                            .weight(0.7f)
                            .fillMaxWidth()
                    )
                    Row(
                        modifier = Modifier.weight(0.2f),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(modifier = Modifier.weight(0.1f)) {
                            Icon(
                                modifier = Modifier.size(20.dp),
                                imageVector = Default.Add,
                                contentDescription = null
                            )
                        }
                        Surface(modifier = Modifier.weight(0.1f)) {
                            Icon(
                                modifier = Modifier.size(20.dp),
                                imageVector = Default.Clear,
                                contentDescription = null
                            )
                        }
                    }
                }
            }
        }

        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = shapes.medium
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ){
                Row(
                    modifier = Modifier.fillMaxWidth(1.0f),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(modifier = Modifier.weight(0.1f)) {
                        Icon(
                            modifier = Modifier.size(20.dp),
                            imageVector = ImageVector.vectorResource(id = country),
                            contentDescription = null
                        )
                    }
                    Text(
                        text = "Business Contacts",
                        fontWeight = Bold,
                        textAlign = Start,
                        overflow = Ellipsis,
                        maxLines = 1,
                        modifier = Modifier
                            .weight(0.7f)
                            .fillMaxWidth()
                    )
                    Row(
                        modifier = Modifier.weight(0.2f),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(modifier = Modifier.weight(0.1f)) {
                            Icon(
                                modifier = Modifier.size(20.dp),
                                imageVector = Default.Add,
                                contentDescription = null
                            )
                        }
                        Surface(modifier = Modifier.weight(0.1f)) {
                            Icon(
                                modifier = Modifier.size(20.dp),
                                imageVector = Default.Clear,
                                contentDescription = null
                            )
                        }
                    }
                }
            }
        }

        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = shapes.medium
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ){
                Row(
                    modifier = Modifier.fillMaxWidth(1.0f),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(modifier = Modifier.weight(0.1f)) {
                        Icon(
                            modifier = Modifier.size(20.dp),
                            imageVector = ImageVector.vectorResource(id = country),
                            contentDescription = null
                        )
                    }
                    Text(
                        text = "Business Links",
                        fontWeight = Bold,
                        textAlign = Start,
                        overflow = Ellipsis,
                        maxLines = 1,
                        modifier = Modifier
                            .weight(0.7f)
                            .fillMaxWidth()
                    )
                    Row(
                        modifier = Modifier.weight(0.2f),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(modifier = Modifier.weight(0.1f)) {
                            Icon(
                                modifier = Modifier.size(20.dp),
                                imageVector = Default.Add,
                                contentDescription = null
                            )
                        }
                        Surface(modifier = Modifier.weight(0.1f)) {
                            Icon(
                                modifier = Modifier.size(20.dp),
                                imageVector = Default.Clear,
                                contentDescription = null
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview(){
    CustomThemes.ApplicationTheme {
        Column(modifier = Modifier.fillMaxSize()) {
            ScreenContent(
                bizProfileDtl = BizProfileDtl(
                    bizProfile = BizProfile(),
                    configCurrent = AppConfig.ConfigCurrent()
                )
            )
        }
    }
}