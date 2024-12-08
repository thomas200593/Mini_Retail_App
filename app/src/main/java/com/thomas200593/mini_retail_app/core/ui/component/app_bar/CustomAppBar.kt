package com.thomas200593.mini_retail_app.core.ui.component.app_bar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.referentialEqualityPolicy
import androidx.compose.runtime.saveable.rememberSaveableStateHolder
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.FloatingWindow
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.LocalOwnersProvider
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filterNot

object CustomAppBar {
    @Composable
    fun TopAppBar(navController: NavController, visible: Boolean) =
        AppBar(
            navController = navController,
            visible = visible
        )

    @Composable
    fun ProvideTopAppBarAction(actions: @Composable RowScope.() -> Unit){
        if(LocalViewModelStoreOwner.current == null ||
            LocalViewModelStoreOwner.current !is NavBackStackEntry) { return }
        val actionViewModel = viewModel(initializer = { TopAppBarViewModel() })
        DisposableEffect(key1 = Unit) {
            actionViewModel.actionState = actions
            onDispose { actionViewModel.actionState = null }
        }
    }

    @Composable
    fun ProvideTopAppBarTitle(title: @Composable () -> Unit){
        if (
            LocalViewModelStoreOwner.current == null ||
            LocalViewModelStoreOwner.current !is NavBackStackEntry
        ){ return }
        val actionViewModel = viewModel(initializer = { TopAppBarViewModel() })
        DisposableEffect(key1 = Unit) {
            actionViewModel.titleState = title
            onDispose { actionViewModel.titleState = null }
        }
    }

    @Composable
    fun ProvideTopAppBarNavigationIcon(navIcon: @Composable () -> Unit){
        val actionViewModel = viewModel(initializer = { TopAppBarViewModel() })
        DisposableEffect(key1 = Unit) {
            actionViewModel.navIconState = navIcon
            onDispose { actionViewModel.navIconState = null }
        }
    }

    @Composable
    private fun RowScope.TopAppBarAction(navBackStackEntry: NavBackStackEntry?){
        val stateHolder = rememberSaveableStateHolder()
        navBackStackEntry?.LocalOwnersProvider(stateHolder) {
            val actionViewModel = viewModel(initializer = { TopAppBarViewModel() })
            actionViewModel.actionState?.let { it() }
        }
    }

    @Composable
    private fun TopAppBarTitle(navBackStackEntry: NavBackStackEntry?) {
        val stateHolder = rememberSaveableStateHolder()
        navBackStackEntry?.LocalOwnersProvider(stateHolder) {
            val actionViewModel = viewModel(initializer = { TopAppBarViewModel() })
            actionViewModel.titleState?.let { it() }
        }
    }

    @Composable
    private fun TopAppBarNavIcon(navBackStackEntry: NavBackStackEntry?) {
        val stateHolder = rememberSaveableStateHolder()
        navBackStackEntry?.LocalOwnersProvider(stateHolder){
            val actionViewModel = viewModel(initializer = { TopAppBarViewModel() })
            actionViewModel.navIconState?.let { it() }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class, FlowPreview::class)
    @Composable
    private fun AppBar(
        navController: NavController,
        modifier: Modifier = Modifier,
        visible: Boolean
    ) {
        val currentContentBackStackEntry by produceState(
            initialValue = null as NavBackStackEntry?,
            producer = {
                navController.currentBackStackEntryFlow.debounce(100)
                    .filterNot { it.destination is FloatingWindow }.collect{ value = it }
            }
        )
        AnimatedVisibility(
            visible = visible,
            enter = fadeIn() + expandVertically(),
            exit = shrinkVertically() + fadeOut()
        ) {
            TopAppBar(
                modifier = modifier,
                navigationIcon = { TopAppBarNavIcon(currentContentBackStackEntry) },
                title = { TopAppBarTitle(currentContentBackStackEntry) },
                actions = { TopAppBarAction(currentContentBackStackEntry) }
            )
        }
    }

    private class TopAppBarViewModel: ViewModel() {
        var navIconState by
        mutableStateOf(null as (@Composable () -> Unit)?, referentialEqualityPolicy())
        var titleState by
        mutableStateOf(null as (@Composable () -> Unit)?, referentialEqualityPolicy())
        var actionState by
        mutableStateOf(null as (@Composable RowScope.() -> Unit)?, referentialEqualityPolicy())
    }
}