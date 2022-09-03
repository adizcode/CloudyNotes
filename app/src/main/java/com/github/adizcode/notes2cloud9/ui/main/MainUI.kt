package com.github.adizcode.notes2cloud9.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FileUpload
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
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.github.adizcode.notes2cloud9.TopBar
import com.github.adizcode.notes2cloud9.navigation.MainNavHost
import com.github.adizcode.notes2cloud9.navigation.MainSubScreens
import com.github.adizcode.notes2cloud9.ui.theme.SecondaryBackground
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainScaffold() {
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed)
    )
    val coroutineScope = rememberCoroutineScope()
    val navController = rememberNavController()


    BottomSheetScaffold(
        scaffoldState = bottomSheetScaffoldState,
        sheetShape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp),
        sheetContent = {
            Box(
                Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.9f)
                    .padding(16.dp)
            ) {
                Text(text = "Hello from sheet", fontSize = 28.sp, fontWeight = FontWeight.Bold)
            }
        }, sheetPeekHeight = 0.dp
    ) {
        Scaffold(modifier = Modifier
            .background(SecondaryBackground),
            backgroundColor = SecondaryBackground,
            topBar = {
                TopBar(Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp))
            },
            floatingActionButton = {
                FloatingActionButton(onClick = {}) {
                    Icon(imageVector = Icons.Filled.FileUpload, contentDescription = null)
                }
            },
            bottomBar = {
                BottomNavigation {
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentDestination = navBackStackEntry?.destination
                    MainSubScreens.allScreens.forEach { screen ->
                        BottomNavigationItem(
                            icon = { Icon(screen.imageVector, contentDescription = null) },
                            label = { Text(screen.label) },
                            selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                            onClick = {

                                if (screen == MainSubScreens.Search) {

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
            }) {
            MainNavHost(modifier = Modifier
                .padding(it)
                .padding(horizontal = 16.dp),
                navController = navController)
        }
    }
}

@Composable
fun HomeUi() {
    LazyColumn {
        item {
            MainSubScreenHeading("Fresh study material: ")
        }
        items(10) {
            Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                Spacer(modifier = Modifier.height(20.dp))
                Card(modifier = Modifier.size(250.dp)) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        AsyncImage(
                            model = "https://images.squarespace-cdn.com/content/v1/51b3dc8ee4b051b96ceb10de/1534799078116-K7SMXJF3P0NT2W92SO6T/image-asset.jpeg",
                            contentDescription = null,
                            modifier = Modifier
                                .weight(0.7f),
                            contentScale = ContentScale.Crop,
                        )
                        Row(modifier = Modifier.weight(0.3f),
                            verticalAlignment = Alignment.CenterVertically) {
                            Text("Descriptive File Name",
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ProfileUi() {
    MyNotes()
}

@Composable
fun MyNotes(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
    ) {
        MainSubScreenHeading("Your notes:")

        LazyColumn(verticalArrangement = Arrangement.Absolute.spacedBy(20.dp)) {
            item {
                Text("Public Notes", fontWeight = FontWeight.Bold, fontSize = 22.sp)
            }
            items(4) {
                if (it > 0) {
                    Spacer(Modifier.height(10.dp))
                }
                NoteBox(num = it)
            }
            item {
                Text("Private Notes", fontWeight = FontWeight.Bold, fontSize = 22.sp)
            }
            items(4) {
                if (it > 0) {
                    Spacer(Modifier.height(10.dp))
                }
                NoteBox(num = it)
            }
        }
    }
}

@Composable
fun MainSubScreenHeading(text: String) {
    Text(text,
        fontSize = 32.sp,
        fontWeight = FontWeight.ExtraBold,
        modifier = Modifier.padding(top = 10.dp))
}

@Composable
fun NoteBox(modifier: Modifier = Modifier, num: Int) {
    Box(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .height(64.dp)
            .clip(RoundedCornerShape(15.dp))
            .background(Color.White)
            .clickable { }
            .padding(16.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text("Note $num", fontWeight = FontWeight.Bold, color = Color(0xFF3A1010))
    }
}