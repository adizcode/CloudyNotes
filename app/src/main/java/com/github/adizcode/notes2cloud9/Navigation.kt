package com.github.adizcode.notes2cloud9

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController

@Composable
fun MyAppNavHost(navController: NavHostController = rememberNavController()) {
    NavHost(navController = navController, startDestination = "authentication") {
        authGraph(navController)
        composable("home") { Home() }
    }
}

fun NavGraphBuilder.authGraph(navController: NavController) {
    navigation(startDestination = "login", route = "authentication") {

        val navigateToHome = {
            navController.navigate("home") {
                popUpTo("authentication") { inclusive = true }
            }
        }

        val navigateToAuthScreen: (String) -> Unit = { route ->
            navController.navigate(route) {
                popUpTo(route) { inclusive = true }
            }
        }

        composable("login") {
            Login(
                navigateToHome = navigateToHome,
                navigateToRegister = { navigateToAuthScreen("register") },
            )
        }
        composable("register") {
            Register(
                navigateToHome = navigateToHome,
                navigateToLogin = { navigateToAuthScreen("login") },
            )
        }
    }
}