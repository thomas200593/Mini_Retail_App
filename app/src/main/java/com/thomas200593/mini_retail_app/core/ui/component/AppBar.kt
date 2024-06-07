package com.thomas200593.mini_retail_app.core.ui.component

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
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
import kotlinx.coroutines.flow.filterNot
import timber.log.Timber

private const val TAG = "AppBar"
object AppBar {
    private class TopAppBarViewModel: ViewModel(){
        var navIconState by mutableStateOf(null as (@Composable () -> Unit)?, referentialEqualityPolicy() )
        var titleState by mutableStateOf(null as (@Composable () -> Unit)?, referentialEqualityPolicy())
        var actionState by mutableStateOf(null as (@Composable RowScope.() -> Unit)?, referentialEqualityPolicy())
    }

    @Composable
    fun ProvideTopAppBarAction(actions: @Composable RowScope.() -> Unit){
        Timber.d("Called %s.ProvideTopAppBarAction()", TAG)
        if(LocalViewModelStoreOwner.current == null || LocalViewModelStoreOwner.current !is NavBackStackEntry){ return }
        val actionViewModel = viewModel(initializer = {TopAppBarViewModel()})
        SideEffect {
            actionViewModel.actionState = actions
        }
    }

    @Composable
    fun ProvideTopAppBarTitle(title: @Composable () -> Unit){
        Timber.d("Called %s.ProvideTopAppBarTitle()", TAG)
        if (LocalViewModelStoreOwner.current == null || LocalViewModelStoreOwner.current !is NavBackStackEntry){ return }
        val actionViewModel = viewModel(initializer = { TopAppBarViewModel() })
        SideEffect {
            actionViewModel.titleState = title
        }
    }

    @Composable
    fun ProvideTopAppBarNavigationIcon(navIcon: @Composable () -> Unit){
        Timber.d("Called %s.ProvideNavigationIcon()", TAG)
        val actionViewModel = viewModel(initializer = { TopAppBarViewModel() })
        SideEffect {
            actionViewModel.navIconState = navIcon
        }
    }

    @Composable
    fun RowScope.TopAppBarAction(navBackStackEntry: NavBackStackEntry?){
        Timber.d("Called %s.TopAppBarAction()", TAG)
        val stateHolder = rememberSaveableStateHolder()
        navBackStackEntry?.LocalOwnersProvider(stateHolder) {
            val actionViewModel = viewModel(initializer = { TopAppBarViewModel() })
            actionViewModel.actionState?.let { it() }
        }
    }

    @Composable
    fun TopAppBarTitle(navBackStackEntry: NavBackStackEntry?) {
        val stateHolder = rememberSaveableStateHolder()
        navBackStackEntry?.LocalOwnersProvider(stateHolder) {
            val actionViewModel = viewModel(initializer = { TopAppBarViewModel() })
            actionViewModel.titleState?.let { it() }
        }
    }

    @Composable
    fun TopAppBarNavIcon(navBackStackEntry: NavBackStackEntry?){
        val stateHolder = rememberSaveableStateHolder()
        navBackStackEntry?.LocalOwnersProvider(stateHolder){
            val actionViewModel = viewModel(initializer = { TopAppBarViewModel() })
            actionViewModel.navIconState?.let { it() }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun AppBar(
        navController: NavController,
        modifier: Modifier = Modifier
    ){
        Timber.d("Called : %s.AppTopBar()", TAG)
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
}