package com.github.adizcode.cloudynotes.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.github.adizcode.cloudynotes.ui.view.core.CoreScaffold
import com.github.adizcode.cloudynotes.ui.viewmodel.MyNotesViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

enum class TopLevelNavRoutes(val route: String) {
    AUTH("authGraph"), CORE("coreScaffold")
}

@Composable
fun MyAppNavHost(
    navController: NavHostController = rememberNavController(),
    viewModel: MyNotesViewModel,
    openFile: () -> Unit,
) {

    val isUserSignedIn = Firebase.auth.currentUser != null
    val startDestination = if (isUserSignedIn) TopLevelNavRoutes.CORE else TopLevelNavRoutes.AUTH

    NavHost(navController = navController, startDestination = startDestination.route) {
        authGraph(navController)
        composable(TopLevelNavRoutes.CORE.route) { CoreScaffold(viewModel, openFile) }
    }
}