package com.github.adizcode.notes2cloud9.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.github.adizcode.notes2cloud9.ui.main.HomeUi
import com.github.adizcode.notes2cloud9.ui.main.ProfileUi
import com.github.adizcode.notes2cloud9.ui.main.SearchUi

sealed class MainSubScreens(val route: String, val label: String) {
    object Home : MainSubScreens("home", "Home")
    object Search : MainSubScreens("search", "Search")
    object Profile : MainSubScreens("profile", "Profile")
}

fun NavGraphBuilder.mainGraph(navController: NavController) {

    navigation(startDestination = MainSubScreens.Profile.route,
        route = TopLevelNavRoutes.MAIN.route) {
        composable(MainSubScreens.Home.route) {
            HomeUi()
        }
        composable(MainSubScreens.Search.route) {
            SearchUi()
        }
        composable(MainSubScreens.Profile.route) {
            ProfileUi()
        }
    }
}