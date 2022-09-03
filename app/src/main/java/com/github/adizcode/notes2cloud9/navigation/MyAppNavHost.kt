package com.github.adizcode.notes2cloud9.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController

enum class NavGraphs(val route: String) {
    AUTH("authGraph"), MAIN("mainGraph")
}

@Composable
fun MyAppNavHost(navController: NavHostController = rememberNavController()) {
    NavHost(navController = navController, startDestination = NavGraphs.AUTH.route) {
        authGraph(navController)
        mainGraph(navController)
    }
}