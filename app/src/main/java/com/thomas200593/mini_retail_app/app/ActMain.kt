package com.thomas200593.mini_retail_app.app

import android.graphics.Color.TRANSPARENT
import android.os.Bundle
import androidx.activity.SystemBarStyle.Companion.auto
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle.State.STARTED
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.thomas200593.mini_retail_app.app.UiStateMain.Loading
import com.thomas200593.mini_retail_app.app.UiStateMain.Success
import com.thomas200593.mini_retail_app.app.ui.LocalStateApp
import com.thomas200593.mini_retail_app.app.ui.ScrApp
import com.thomas200593.mini_retail_app.app.ui.rememberStateApplication
import com.thomas200593.mini_retail_app.core.data.local.session.Session
import com.thomas200593.mini_retail_app.core.design_system.network_monitor.NetworkMonitor
import com.thomas200593.mini_retail_app.core.ui.common.Colors.darkScrim
import com.thomas200593.mini_retail_app.core.ui.common.Colors.lightScrim
import com.thomas200593.mini_retail_app.core.ui.common.Themes.ApplicationTheme
import com.thomas200593.mini_retail_app.core.ui.common.Themes.calculateInitialFontSize
import com.thomas200593.mini_retail_app.core.ui.common.Themes.shouldUseDarkTheme
import com.thomas200593.mini_retail_app.core.ui.common.Themes.shouldUseDynamicColor
import com.thomas200593.mini_retail_app.core.ui.component.Splashscreen.setupSplashscreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ActMain: AppCompatActivity() {

    @Inject lateinit var networkMonitor: NetworkMonitor
    @Inject lateinit var session: Session

    private val vm: VMMain by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashscreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        var uiState: UiStateMain by mutableStateOf(Loading)
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(STARTED)
            { launch { vm.uiState.onEach { uiState = it }.collect() } }
        }
        splashscreen.setKeepOnScreenCondition{
            when(uiState){
                Loading -> true
                is Success -> false
            }
        }
        setupSplashscreen(splashscreen)
        enableEdgeToEdge()
        setContent {
            val darkTheme = shouldUseDarkTheme(uiState = uiState)
            val dynamicColor = shouldUseDynamicColor(uiState = uiState)
            val fontSize = calculateInitialFontSize(uiState = uiState)

            DisposableEffect(key1 = darkTheme) {
                enableEdgeToEdge(
                    statusBarStyle = auto(TRANSPARENT, TRANSPARENT) { darkTheme },
                    navigationBarStyle = auto(lightScrim = lightScrim, darkScrim = darkScrim) { darkTheme }
                ); onDispose {}
            }

            val appState = rememberStateApplication(networkMonitor = networkMonitor, session = session)

            CompositionLocalProvider(LocalStateApp provides appState) {
                ApplicationTheme(
                    darkTheme = darkTheme,
                    dynamicColor = dynamicColor,
                    fontSize = fontSize,
                    content = { ScrApp() }
                )
            }
        }
    }
}