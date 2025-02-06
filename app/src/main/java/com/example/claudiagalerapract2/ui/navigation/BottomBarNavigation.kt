package com.example.claudiagalerapract2.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.claudiagalerapract2.ui.common.Constantes

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = backStackEntry?.destination

    NavigationBar {
        NavigationBarItem(
            selected = currentDestination?.hierarchy?.any { it.route == Constantes.TODOS } == true,
            onClick = {
                if (currentDestination?.route != Constantes.TODOS) {
                    navController.navigate(Constantes.TODOS) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            },
            icon = { Icon(Icons.AutoMirrored.Filled.List, contentDescription = Constantes.TODOS) },
            label = { Text(Constantes.TODOS) }
        )
        NavigationBarItem(
            selected = currentDestination?.hierarchy?.any { it.route == Constantes.ALBUMS } == true,
            onClick = {
                if (currentDestination?.route != Constantes.ALBUMS) {
                    navController.navigate(Constantes.ALBUMS) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            },
            icon = { Icon(Icons.Default.AccountBox, contentDescription = Constantes.ALBUMS) },
            label = { Text(Constantes.ALBUMS) }
        )
    }
}
