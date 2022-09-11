package com.github.adizcode.cloudynotes.ui.view.core

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetState
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FileUpload
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.github.adizcode.cloudynotes.ui.navigation.CoreNavHost
import com.github.adizcode.cloudynotes.ui.navigation.CoreSubScreen
import com.github.adizcode.cloudynotes.ui.theme.Background
import com.github.adizcode.cloudynotes.ui.theme.SecondaryBackground
import com.github.adizcode.cloudynotes.ui.view.core.subscreen.SearchBottomSheet
import com.github.adizcode.cloudynotes.ui.viewmodel.NotesViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CoreScaffold(viewModel: NotesViewModel, openFile: () -> Unit) {
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed)
    )
    val coroutineScope = rememberCoroutineScope()
    val navController = rememberNavController()


    BottomSheetScaffold(
        scaffoldState = bottomSheetScaffoldState,
        sheetShape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp),
        sheetContent = { SearchBottomSheet() }, sheetPeekHeight = 0.dp,
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        val currentRoute = currentDestination?.route
        val isCurrentRouteNotNoteUpload =
            currentRoute == null || currentRoute != CoreSubScreen.NoteUpload.route

        Scaffold(
            modifier = Modifier
                .background(SecondaryBackground),
            backgroundColor = SecondaryBackground,
            topBar = {
                Card(elevation = 10.dp, backgroundColor = Background) {
                    TopBar(Modifier
                        .background(Background)
                        .padding(16.dp))
                }
            },
            floatingActionButton = {
                if (isCurrentRouteNotNoteUpload) {
                    FloatingActionButton(onClick = {
                        navController.navigate(CoreSubScreen.NoteUpload.route)
                    }, backgroundColor = Background) {
                        Icon(imageVector = Icons.Filled.FileUpload, contentDescription = null)
                    }
                }
            },
            bottomBar = {
                if (isCurrentRouteNotNoteUpload) {
                    BottomNavigation(backgroundColor = Background) {
                        CoreSubScreen.bottomNavBarScreens.forEach { screen ->
                            BottomNavigationItem(
                                icon = { Icon(screen.imageVector, contentDescription = null) },
                                label = { Text(screen.label) },
                                selected = currentRoute?.equals(screen.route) ?: false,
                                onClick = {

                                    if (screen == CoreSubScreen.Search) {

                                        coroutineScope.launch {

                                            if (bottomSheetScaffoldState.bottomSheetState.isCollapsed) {
                                                bottomSheetScaffoldState.bottomSheetState.expand()
                                            } else {
                                                bottomSheetScaffoldState.bottomSheetState.collapse()
                                            }
                                        }
                                    } else {
                                        navController.navigate(screen.route) {
                                            // Pop up to the start destination of the graph to
                                            // avoid building up a large stack of destinations
                                            // on the back stack as users select items
                                            popUpTo(navController.graph.findStartDestination().id) {
                                                saveState = true
                                            }
                                            // Avoid multiple copies of the same destination when
                                            // reselecting the same item
                                            launchSingleTop = true
                                            // Restore state when reselecting a previously selected item
                                            restoreState = true
                                        }
                                    }
                                }
                            )
                        }
                    }
                }
            },
        ) {
            CoreNavHost(
                modifier = Modifier
                    .padding(it)
                    .padding(horizontal = 16.dp),
                navController = navController,
                viewModel = viewModel,
                openFile = openFile,
            )
        }
    }
}

@Composable
fun TopBar(modifier: Modifier = Modifier) {

    Row(modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Absolute.spacedBy(10.dp),
        ) {
            AsyncImage(
                model = "https://4dhealth.ca/wp-content/uploads/2021/04/happy.jpg",
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .border(4.dp, Color.White, CircleShape)
                    .clickable { },
            )
            Text("Hi, how are you?", fontSize = 18.sp, fontWeight = FontWeight.Medium)
        }
        IconButton(onClick = { /*TODO*/ }) {
            Icon(modifier = Modifier.size(28.dp),
                imageVector = Icons.Filled.Notifications,
                contentDescription = null)
        }
    }
}