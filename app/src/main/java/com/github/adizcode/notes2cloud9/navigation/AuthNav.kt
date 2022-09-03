package com.github.adizcode.notes2cloud9.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.github.adizcode.notes2cloud9.ui.auth.Login
import com.github.adizcode.notes2cloud9.ui.auth.Register

enum class AuthScreens {
    LOGIN, REGISTER
}

fun NavGraphBuilder.authGraph(navController: NavController) {
    navigation(startDestination = AuthScreens.LOGIN.toString(), route = NavGraphs.AUTH.route) {

        val navigateToMainGraph = {
            navController.navigate(NavGraphs.MAIN.route) {
                popUpTo(NavGraphs.AUTH.route) { inclusive = true }
            }
        }

        val navigateToAuthScreen: (String) -> Unit = { route ->
            navController.navigate(route) {
                popUpTo(route) { inclusive = true }
            }
        }

        composable(AuthScreens.LOGIN.toString()) {
            Login(
                navigateToHome = navigateToMainGraph,
                navigateToRegister = { navigateToAuthScreen(AuthScreens.REGISTER.toString()) },
            )
        }
        composable(AuthScreens.REGISTER.toString()) {
            Register(
                navigateToHome = navigateToMainGraph,
                navigateToLogin = { navigateToAuthScreen(AuthScreens.LOGIN.toString()) },
            )
        }
    }
}