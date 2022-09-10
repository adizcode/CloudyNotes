package com.github.adizcode.cloudynotes.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.github.adizcode.cloudynotes.ui.NotesViewModel
import com.github.adizcode.cloudynotes.ui.view.main.MainScaffold
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

enum class TopLevelNavRoutes(val route: String) {
    AUTH("authGraph"), MAIN("mainScaffold")
}

@Composable
fun MyAppNavHost(
    navController: NavHostController = rememberNavController(),
    viewModel: NotesViewModel,
    openFile: () -> Unit,
) {

    val isUserSignedIn = Firebase.auth.currentUser != null
    val startDestination = if (isUserSignedIn) TopLevelNavRoutes.MAIN else TopLevelNavRoutes.AUTH

    NavHost(navController = navController, startDestination = startDestination.route) {
        authGraph(navController, viewModel)
        composable(TopLevelNavRoutes.MAIN.route) { MainScaffold(viewModel, openFile) }
    }
}