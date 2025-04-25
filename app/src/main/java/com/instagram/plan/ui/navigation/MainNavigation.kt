package com.instagram.plan.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.instagram.plan.ui.components.BottomNavigationBar
import com.instagram.plan.ui.screens.BioSitesScreen
import com.instagram.plan.ui.screens.PlanScreen
import com.instagram.plan.ui.screens.ProjectsScreen
import com.instagram.plan.ui.screens.StudioScreen
import com.instagram.plan.ui.screens.TemplatesScreen

@Composable
fun MainNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: Screen.Plan.route

    androidx.compose.material3.Scaffold(
        modifier = modifier,
        bottomBar = {
            BottomNavigationBar(
                currentRoute = currentRoute,
                onItemClick = { screen ->
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                }
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Plan.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Studio.route) { StudioScreen() }
            composable(Screen.Templates.route) { TemplatesScreen() }
            composable(Screen.Projects.route) { ProjectsScreen() }
            composable(Screen.Plan.route) { PlanScreen() }
            composable(Screen.BioSites.route) { BioSitesScreen() }
        }
    }
}