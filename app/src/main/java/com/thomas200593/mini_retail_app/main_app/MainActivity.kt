package com.thomas200593.mini_retail_app.main_app

import android.graphics.Color.TRANSPARENT
import android.os.Bundle
import androidx.activity.SystemBarStyle.Companion.auto
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle.State.STARTED
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.thomas200593.mini_retail_app.core.design_system.util.NetworkMonitor
import com.thomas200593.mini_retail_app.core.ui.common.AppColor.darkScrim
import com.thomas200593.mini_retail_app.core.ui.common.AppColor.lightScrim
import com.thomas200593.mini_retail_app.core.ui.common.AppTheme.ApplicationTheme
import com.thomas200593.mini_retail_app.core.ui.common.AppTheme.calculateInitialFontSize
import com.thomas200593.mini_retail_app.core.ui.common.AppTheme.shouldUseDarkTheme
import com.thomas200593.mini_retail_app.core.ui.common.AppTheme.shouldUseDynamicColor
import com.thomas200593.mini_retail_app.core.ui.component.AppSplashscreen.setupSplashscreen
import com.thomas200593.mini_retail_app.main_app.MainActivityUiState.Loading
import com.thomas200593.mini_retail_app.main_app.MainActivityUiState.Success
import com.thomas200593.mini_retail_app.main_app.ui.AppScreen
import com.thomas200593.mini_retail_app.main_app.ui.rememberAppState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

private const val TAG = "MainActivity"

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@AndroidEntryPoint
class MainActivity: AppCompatActivity() {

    @Inject lateinit var networkMonitor: NetworkMonitor
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        Timber.d("Called : %s.onCreate()", TAG)
        val splashscreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        var uiState: MainActivityUiState by mutableStateOf(Loading)
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(STARTED){
                launch {
                    viewModel.uiState.onEach {
                        uiState = it
                    }.collect()
                }
            }
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

            val darkTheme = shouldUseDarkTheme(uiState)
            val dynamicColor = shouldUseDynamicColor(uiState)
            val font = calculateInitialFontSize(uiState)

            DisposableEffect(key1 = darkTheme) {
                enableEdgeToEdge(
                    statusBarStyle = auto(
                        TRANSPARENT,
                        TRANSPARENT
                    ){ darkTheme },
                    navigationBarStyle = auto(
                        lightScrim,
                        darkScrim,
                    ){ darkTheme }
                )
                onDispose {}
            }

            val appState = rememberAppState(
                windowsSizeClass = calculateWindowSizeClass(activity = this),
                networkMonitor = networkMonitor,
            )

            CompositionLocalProvider {
                ApplicationTheme(
                    darkTheme = darkTheme,
                    dynamicColor = dynamicColor,
                    font = font,
                    content = {
                        AppScreen(appState = appState)
                    }
                )
            }
        }
    }
}