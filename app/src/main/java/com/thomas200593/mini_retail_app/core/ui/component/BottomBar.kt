package com.thomas200593.mini_retail_app.core.ui.component

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import com.thomas200593.mini_retail_app.core.ui.component.BottomBar.BottomNavigationBarDefaults.navigationContentColor
import com.thomas200593.mini_retail_app.core.ui.component.BottomBar.BottomNavigationBarDefaults.navigationIndicatorColor
import com.thomas200593.mini_retail_app.core.ui.component.BottomBar.BottomNavigationBarDefaults.navigationSelectedItemColor
import com.thomas200593.mini_retail_app.app.navigation.TopLevelDestination

object BottomBar {
    @Composable
    fun BottomBar(
        destinations: List<TopLevelDestination>,
        onNavigateToDestination: (TopLevelDestination) -> Unit,
        currentDestination: NavDestination?,
        modifier: Modifier = Modifier
    ) {
        BottomNavBar(
            modifier = modifier
        ){
            destinations.forEach { destination ->
                val selected = currentDestination.isTopLevelDestinationInHierarchy(destination)
                BottomNavigationBarItems(
                    selected = selected,
                    onClick = { onNavigateToDestination(destination) },
                    icon = {
                        Icon(imageVector = ImageVector.vectorResource(destination.unselectedIcon), contentDescription = null)
                    },
                    selectedIcon = {
                        Icon(imageVector = ImageVector.vectorResource(destination.selectedIcon), contentDescription = null)
                    },
                    label = {
                        Text(text = stringResource(id = destination.iconTextId))
                    },
                    modifier = Modifier.size(28.dp)
                )
            }
        }
    }

    @Composable
    private fun BottomNavBar(
        modifier: Modifier = Modifier,
        content: @Composable RowScope.() -> Unit
    ) {
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
                disabledIconColor = Color.Transparent,
                disabledTextColor = Color.Transparent
            )
        )
    }

    private object BottomNavigationBarDefaults{
        @Composable
        fun navigationContentColor() = MaterialTheme.colorScheme.onSurfaceVariant
        @Composable
        fun navigationSelectedItemColor() = MaterialTheme.colorScheme.onPrimaryContainer
        @Composable
        fun navigationIndicatorColor() = MaterialTheme.colorScheme.primaryContainer
    }

    private fun NavDestination?.isTopLevelDestinationInHierarchy(destination: TopLevelDestination) =
        this
            ?.hierarchy
            ?.any{
                it.route?.contains(destination.route, true)?:false
            } ?:false
}