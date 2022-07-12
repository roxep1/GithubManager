package com.bashkir.githubmanager.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.bashkir.githubmanager.ui.BottomBarNavigation
import com.bashkir.githubmanager.ui.components.MainBottomNavigationView

@Composable
fun BottomNavigationScreen(navController: NavController) {
    val bottomNavigationController = rememberNavController()

    Scaffold(
        bottomBar = {
            MainBottomNavigationView(
                bottomNavController = bottomNavigationController
            )
        }
    ) {
        Box(Modifier.padding(it)) {
            BottomBarNavigation(
                bottomNavigationController,
                navController
            )
        }
    }
}