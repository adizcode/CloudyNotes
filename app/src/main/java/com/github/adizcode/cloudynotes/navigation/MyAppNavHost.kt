package com.github.adizcode.cloudynotes.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.github.adizcode.cloudynotes.ui.main.MainScaffold

enum class TopLevelNavRoutes(val route: String) {
    AUTH("authGraph"), MAIN("mainScaffold")
}

@Composable
fun MyAppNavHost(navController: NavHostController = rememberNavController()) {
    NavHost(navController = navController, startDestination = TopLevelNavRoutes.AUTH.route) {
        authGraph(navController)
        composable(TopLevelNavRoutes.MAIN.route) { MainScaffold() }
    }
}