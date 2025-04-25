package com.instagram.plan.ui.navigation

import com.instagram.plan.R

sealed class Screen(
    val route: String,
    val title: String,
    val icon: Int
) {
    object Studio : Screen("studio", "Studio", R.drawable.outline_all_inclusive_24)
    object Templates : Screen("templates", "Templates", R.drawable.outline_auto_awesome_mosaic_24)
    object Projects : Screen("projects", "Projects", R.drawable.outline_library_add_24)
    object Plan : Screen("plan", "Plan", R.drawable.outline_calendar_today_24)
    object BioSites : Screen("biosites", "Bio Sites", R.drawable.outline_spoke_24)
}