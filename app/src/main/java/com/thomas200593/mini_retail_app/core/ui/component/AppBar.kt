package com.thomas200593.mini_retail_app.core.ui.component

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
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner.current
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.FloatingWindow
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.LocalOwnersProvider
import kotlinx.coroutines.flow.filterNot
import timber.log.Timber

private val TAG = AppBar::class.simpleName

object AppBar {
    @Composable
    fun TopAppBar(navController: NavController){
        Timber.d("Called : fun $TAG.TopAppBar()")
        AppBar(navController = navController)
    }

    @Composable
    fun ProvideTopAppBarAction(actions: @Composable RowScope.() -> Unit){
        Timber.d("Called : fun $TAG.ProvideTopAppBarAction()")
        if(current == null || current !is NavBackStackEntry){ return }
        val actionViewModel = viewModel(initializer = {TopAppBarViewModel()})
        DisposableEffect(key1 = Unit) {
            actionViewModel.actionState = actions
            onDispose {
                actionViewModel.actionState = null
            }
        }
    }

    @Composable
    fun ProvideTopAppBarTitle(title: @Composable () -> Unit){
        Timber.d("Called : fun $TAG.ProvideTopAppBarTitle()")
        if (current == null || current !is NavBackStackEntry){ return }
        val actionViewModel = viewModel(initializer = { TopAppBarViewModel() })
        DisposableEffect(key1 = Unit) {
            actionViewModel.titleState = title
            onDispose {
                actionViewModel.titleState = null
            }
        }
    }

    @Composable
    fun ProvideTopAppBarNavigationIcon(navIcon: @Composable () -> Unit){
        Timber.d("Called : fun $TAG.ProvideNavigationIcon()")
        val actionViewModel = viewModel(initializer = { TopAppBarViewModel() })
        DisposableEffect(key1 = Unit) {
            actionViewModel.navIconState = navIcon
            onDispose {
                actionViewModel.navIconState = null
            }
        }
    }

    @Composable
    private fun RowScope.TopAppBarAction(navBackStackEntry: NavBackStackEntry?){
        Timber.d("Called : fun $TAG.RowScope.TopAppBarAction()")
        val stateHolder = rememberSaveableStateHolder()
        navBackStackEntry?.LocalOwnersProvider(stateHolder) {
            val actionViewModel = viewModel(initializer = { TopAppBarViewModel() })
            actionViewModel.actionState?.let { it() }
        }
    }

    @Composable
    private fun TopAppBarTitle(navBackStackEntry: NavBackStackEntry?) {
        Timber.d("Called : fun $TAG.TopAppBarTitle()")
        val stateHolder = rememberSaveableStateHolder()
        navBackStackEntry?.LocalOwnersProvider(stateHolder) {
            val actionViewModel = viewModel(initializer = { TopAppBarViewModel() })
            actionViewModel.titleState?.let { it() }
        }
    }

    @Composable
    private fun TopAppBarNavIcon(navBackStackEntry: NavBackStackEntry?){
        Timber.d("Called : fun $TAG.TopAppBarNavIcon()")
        val stateHolder = rememberSaveableStateHolder()
        navBackStackEntry?.LocalOwnersProvider(stateHolder){
            val actionViewModel = viewModel(initializer = { TopAppBarViewModel() })
            actionViewModel.navIconState?.let { it() }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun AppBar(
        navController: NavController,
        modifier: Modifier = Modifier
    ){
        Timber.d("Called : fun $TAG.AppBar()")
        val currentContentBackStackEntry by produceState(
            initialValue = null as NavBackStackEntry?,
            producer = {
                navController
                    .currentBackStackEntryFlow
                    .filterNot { it.destination is FloatingWindow }
                    .collect{ value = it }
            }
        )
        TopAppBar(
            modifier = modifier,
            navigationIcon = {
                TopAppBarNavIcon(currentContentBackStackEntry)
            },
            title = {
                TopAppBarTitle(currentContentBackStackEntry)
            },
            actions = {
                TopAppBarAction(currentContentBackStackEntry)
            }
        )
    }

    private class TopAppBarViewModel: ViewModel(){
        var navIconState by mutableStateOf(null as (@Composable () -> Unit)?, referentialEqualityPolicy() )
        var titleState by mutableStateOf(null as (@Composable () -> Unit)?, referentialEqualityPolicy())
        var actionState by mutableStateOf(null as (@Composable RowScope.() -> Unit)?, referentialEqualityPolicy())
    }
}