package com.github.adizcode.notes2cloud9.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.github.adizcode.notes2cloud9.ui.main.HomeUi
import com.github.adizcode.notes2cloud9.ui.main.MyNotes
import com.github.adizcode.notes2cloud9.ui.main.SearchUi

sealed class MainScreens(val route: String, val label: String) {
    object Home : MainScreens("home", "Home")
    object Search : MainScreens("search", "Search")
    object Profile : MainScreens("profile", "Profile")
}

fun NavGraphBuilder.mainGraph(navController: NavController) {

    navigation(startDestination = MainScreens.Profile.route, route =  NavGraphs.MAIN.route) {
        composable(MainScreens.Home.route) {
            HomeUi()
        }
        composable(MainScreens.Search.route) {
            SearchUi()
        }
        composable(MainScreens.Profile.route) {
            // ProfileUi()
            MyNotes()
        }
    }
}