package com.github.adizcode.cloudynotes.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notes
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Upload
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.github.adizcode.cloudynotes.ui.NotesViewModel
import com.github.adizcode.cloudynotes.ui.view.main.subscreens.HomeSubScreen
import com.github.adizcode.cloudynotes.ui.view.main.subscreens.MyNotesSubScreen
import com.github.adizcode.cloudynotes.ui.view.main.subscreens.NoteUploadSubScreen

sealed class MainSubScreens(val route: String, val label: String, val imageVector: ImageVector) {
    object Home : MainSubScreens("home", "Home", Icons.Filled.Home)
    object Search : MainSubScreens("search", "Search", Icons.Filled.Search)
    object MyNotes : MainSubScreens("myNotes", "My Notes", Icons.Filled.Notes)
    object NoteUpload : MainSubScreens("noteUpload", "Upload Note", Icons.Filled.Upload)

    companion object {
        val bottomNavBarScreens = listOf(
            Home,
            Search,
            MyNotes,
        )
    }
}

@Composable
fun MainNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    openFile: () -> Unit,
    viewModel: NotesViewModel,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = MainSubScreens.Home.route,
    ) {
        composable(MainSubScreens.Home.route) {
            HomeSubScreen()
        }
        composable(MainSubScreens.MyNotes.route) {
            MyNotesSubScreen()
        }
        composable(MainSubScreens.NoteUpload.route) {
            NoteUploadSubScreen(viewModel = viewModel, openFile = openFile)
        }
    }
}