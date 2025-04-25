package com.instagram.plan.ui.components

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.instagram.plan.ui.navigation.Screen

@Composable
fun BottomNavigationBar(
    currentRoute: String,
    onItemClick: (Screen) -> Unit
) {
    val screens = listOf(
        Screen.Studio,
        Screen.Templates,
        Screen.Projects,
        Screen.Plan,
        Screen.BioSites
    )

    NavigationBar(
        containerColor = Color.White,
        contentColor = Color.Black
    ) {
        screens.forEach { screen ->
            NavigationBarItem(
                selected = currentRoute == screen.route,
                onClick = { onItemClick(screen) },
                icon = {
                    Icon(
                        painter = painterResource(id = screen.icon),
                        contentDescription = screen.title
                    )
                },
                label = { Text(text = screen.title) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.Black,
                    selectedTextColor = Color.Black,
                    unselectedIconColor = Color.Gray,
                    unselectedTextColor = Color.Gray,
                    indicatorColor = Color.White
                )
            )
        }
    }
}

@Preview
@Composable
fun BottomNavigationBarPreview() {
    BottomNavigationBar(currentRoute = Screen.Plan.route, onItemClick = {})
}