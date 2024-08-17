package com.thomas200593.mini_retail_app.core.ui.component

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import com.thomas200593.mini_retail_app.app.navigation.DestTopLevel
import com.thomas200593.mini_retail_app.core.ui.component.CustomBottomBar.BottomNavigationBarDefaults.navigationContentColor
import com.thomas200593.mini_retail_app.core.ui.component.CustomBottomBar.BottomNavigationBarDefaults.navigationIndicatorColor
import com.thomas200593.mini_retail_app.core.ui.component.CustomBottomBar.BottomNavigationBarDefaults.navigationSelectedItemColor

object CustomBottomBar {
    @Composable
    fun BottomBar(
        destinations: List<DestTopLevel>,
        onNavigateToDestination: (DestTopLevel) -> Unit,
        currentDestination: NavDestination?,
        modifier: Modifier = Modifier
    ) {
        BottomNavBar(modifier = modifier){
            destinations.forEach { destination ->
                val selected = currentDestination.isDestinationTopLevelInHierarchy(destination)
                BottomNavigationBarItems(
                    selected = selected,
                    onClick = { onNavigateToDestination(destination) },
                    icon = {
                        destination.scrGraphs.iconRes?.let {
                            ImageVector.vectorResource(
                                it
                            )
                        }?.let {
                            Icon(
                                imageVector = it,
                                contentDescription = null
                            )
                        }
                    },
                    selectedIcon = {
                        destination.scrGraphs.iconRes?.let {
                            ImageVector.vectorResource(
                                it
                            )
                        }?.let {
                            Icon(
                                imageVector = it,
                                contentDescription = null
                            )
                        }
                    },
                    label = { destination.scrGraphs.title?.let { Text(text = stringResource(id = it)) } },
                    modifier = Modifier.size(28.dp)
                )
            }
        }
    }

    @Composable
    private fun BottomNavBar(modifier: Modifier = Modifier, content: @Composable RowScope.() -> Unit) {
        NavigationBar(
            modifier = modifier,
            contentColor = navigationContentColor(),
            tonalElevation = 0.dp,
            content = content
        )
    }

    @Composable
    private fun RowScope.BottomNavigationBarItems(
        modifier: Modifier = Modifier,
        selected: Boolean,
        onClick: () -> Unit,
        icon: @Composable () -> Unit,
        selectedIcon: @Composable () -> Unit,
        enabled: Boolean = true,
        label: @Composable (() -> Unit)? = null,
        alwaysShowLabel: Boolean = true
    ) {
        NavigationBarItem(
            modifier = modifier,
            selected = selected,
            onClick = onClick,
            icon = if(selected) selectedIcon else icon,
            enabled = enabled,
            label = label,
            alwaysShowLabel = alwaysShowLabel,
            colors = NavigationBarItemColors(
                selectedIconColor = navigationSelectedItemColor(),
                unselectedIconColor = navigationContentColor(),
                selectedTextColor = navigationSelectedItemColor(),
                unselectedTextColor = navigationContentColor(),
                selectedIndicatorColor = navigationIndicatorColor(),
                disabledIconColor = Transparent,
                disabledTextColor = Transparent
            )
        )
    }

    private object BottomNavigationBarDefaults{
        @Composable
        fun navigationContentColor() = colorScheme.onSurfaceVariant
        @Composable
        fun navigationSelectedItemColor() = colorScheme.onPrimaryContainer
        @Composable
        fun navigationIndicatorColor() = colorScheme.primaryContainer
    }

    private fun NavDestination?.isDestinationTopLevelInHierarchy(destination: DestTopLevel) =
        this?.hierarchy?.any{
            it.route?.contains(other = destination.scrGraphs.route, ignoreCase = true) ?:false
        } ?:false
}