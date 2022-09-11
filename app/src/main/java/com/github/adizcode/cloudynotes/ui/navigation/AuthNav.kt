package com.github.adizcode.cloudynotes.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.github.adizcode.cloudynotes.ui.view.auth.LoginScreen
import com.github.adizcode.cloudynotes.ui.view.auth.RegisterScreen
import com.github.adizcode.cloudynotes.ui.viewmodel.NotesViewModel

enum class AuthScreens {
    LOGIN, REGISTER
}

fun NavGraphBuilder.authGraph(navController: NavController, viewModel: NotesViewModel) {
    navigation(startDestination = AuthScreens.LOGIN.toString(),
        route = TopLevelNavRoutes.AUTH.route) {

        val navigateToCoreGraph = {
            navController.navigate(TopLevelNavRoutes.CORE.route) {
                popUpTo(TopLevelNavRoutes.AUTH.route) { inclusive = true }
            }
        }

        val navigateToAuthScreen: (String) -> Unit = { route ->
            navController.navigate(route) {
                popUpTo(route) { inclusive = true }
            }
        }

        composable(AuthScreens.LOGIN.toString()) {
            LoginScreen(
                navigateToHome = navigateToCoreGraph,
                navigateToRegister = { navigateToAuthScreen(AuthScreens.REGISTER.toString()) },
                viewModel = viewModel
            )
        }

        composable(AuthScreens.REGISTER.toString()) {
            RegisterScreen(
                navigateToHome = navigateToCoreGraph,
                navigateToLogin = { navigateToAuthScreen(AuthScreens.LOGIN.toString()) },
                viewModel = viewModel
            )
        }
    }
}