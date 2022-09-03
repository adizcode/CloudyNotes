package com.github.adizcode.cloudynotes.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.github.adizcode.cloudynotes.ui.main.MainScaffold
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

enum class TopLevelNavRoutes(val route: String) {
    AUTH("authGraph"), MAIN("mainScaffold")
}

@Composable
fun MyAppNavHost(navController: NavHostController = rememberNavController()) {

    val isUserSignedIn = Firebase.auth.currentUser != null
    val startDestination = if (isUserSignedIn) TopLevelNavRoutes.MAIN else TopLevelNavRoutes.AUTH

    NavHost(navController = navController, startDestination = startDestination.route) {
        authGraph(navController)
        composable(TopLevelNavRoutes.MAIN.route) { MainScaffold() }
    }
}