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
import com.github.adizcode.cloudynotes.ui.view.core.subscreen.HomeSubScreen
import com.github.adizcode.cloudynotes.ui.view.core.subscreen.MyNotesSubScreen
import com.github.adizcode.cloudynotes.ui.view.core.subscreen.NoteUploadSubScreen
import com.github.adizcode.cloudynotes.ui.viewmodel.NotesViewModel

sealed class CoreSubScreen(val route: String, val label: String, val imageVector: ImageVector) {
    object Home : CoreSubScreen("home", "Home", Icons.Filled.Home)
    object Search : CoreSubScreen("search", "Search", Icons.Filled.Search)
    object MyNotes : CoreSubScreen("myNotes", "My Notes", Icons.Filled.Notes)
    object NoteUpload : CoreSubScreen("noteUpload", "Upload Note", Icons.Filled.Upload)

    companion object {
        val bottomNavBarScreens = listOf(
            Home,
            Search,
            MyNotes,
        )
    }
}

@Composable
fun CoreNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    openFile: () -> Unit,
    viewModel: NotesViewModel,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = CoreSubScreen.Home.route,
    ) {
        composable(CoreSubScreen.Home.route) {
            HomeSubScreen()
        }
        composable(CoreSubScreen.MyNotes.route) {
            MyNotesSubScreen()
        }
        composable(CoreSubScreen.NoteUpload.route) {
            NoteUploadSubScreen(viewModel = viewModel,
                openFile = openFile,
                goBack = { navController.popBackStack() })
        }
    }
}