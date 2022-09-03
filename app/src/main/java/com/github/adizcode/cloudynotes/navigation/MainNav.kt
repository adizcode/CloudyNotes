package com.github.adizcode.cloudynotes.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notes
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.github.adizcode.cloudynotes.ui.main.HomeUi
import com.github.adizcode.cloudynotes.ui.main.MyNotesUi

sealed class MainSubScreens(val route: String, val label: String, val imageVector: ImageVector) {
    object Home : MainSubScreens("home", "Home", Icons.Filled.Home)
    object Search : MainSubScreens("search", "Search", Icons.Filled.Search)
    object MyNotes : MainSubScreens("myNotes", "My Notes", Icons.Filled.Notes)

    companion object {
        val allScreens = listOf(
            Home,
            Search,
            MyNotes,
        )
    }
}

@Composable
fun MainNavHost(modifier: Modifier = Modifier, navController: NavHostController) {
    NavHost(modifier = modifier,
        navController = navController,
        startDestination = MainSubScreens.Home.route) {
        composable(MainSubScreens.Home.route) {
            HomeUi()
        }
        composable(MainSubScreens.MyNotes.route) {
            MyNotesUi()
        }
    }
}