package com.bashkir.githubmanager.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.bashkir.githubmanager.data.GithubManagerViewModel
import com.bashkir.githubmanager.ui.screens.AboutScreen
import com.bashkir.githubmanager.ui.screens.BottomNavigationScreen
import com.bashkir.githubmanager.ui.screens.SearchScreen

private const val MAIN_ROUTE = "main"

enum class Screen(val destination: String) {
    Search("search"),
    About("about"),
    BottomNav("bottom");

    companion object {
        val bottomNavigationScreens =
            listOf(Search to Icons.Default.Search, About to Icons.Default.Info)
    }
}

@Composable
fun MainNavigation(navHostController: NavHostController) =
    NavHost(
        navController = navHostController,
        startDestination = Screen.BottomNav.destination,
        route = MAIN_ROUTE
    ) {
        composable(Screen.BottomNav.destination) {
            BottomNavigationScreen(navController = navHostController)
        }
    }

@Composable
fun BottomBarNavigation(navHostController: NavHostController, mainNavController: NavController) =
    NavHost(
        navController = navHostController,
        startDestination = Screen.Search.destination
    ) {
        composable(Screen.Search.destination) {
            SearchScreen(viewModel = mainNavController.getViewModel(it))
        }

        composable(Screen.About.destination) {
            AboutScreen()
        }
    }

@Composable
private fun NavController.getViewModel(backStackEntry: NavBackStackEntry): GithubManagerViewModel =
    hiltViewModel(remember(backStackEntry) { getBackStackEntry(MAIN_ROUTE) })