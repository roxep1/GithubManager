package com.bashkir.githubmanager.ui.components

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.bashkir.githubmanager.ui.Screen

@Composable
fun MainBottomNavigationView(
    bottomNavController: NavController,
    modifier: Modifier = Modifier
) =
    BottomNavigation(modifier) {
        val navBackStackEntry by bottomNavController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        Screen.bottomNavigationScreens.forEach {
            val screen = it.first
            val icon = it.second
            BottomNavigationItem(
                icon = { Icon(icon, null) },
                selected = currentDestination?.hierarchy?.any { destination -> destination.route == screen.destination } == true,
                onClick = {
                    bottomNavController.navigate(screen.destination) {
                        popUpTo(bottomNavController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }