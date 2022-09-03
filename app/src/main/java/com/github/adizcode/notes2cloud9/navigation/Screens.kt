package com.github.adizcode.notes2cloud9.navigation

sealed class Screens(val route: String, val label: String) {
    object Home : Screens("home", "Home")
    object Search : Screens("search", "Search")
    object Profile : Screens("profile", "Profile")
}
