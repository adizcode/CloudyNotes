package com.github.adizcode.cloudynotes.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetState
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FileUpload
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Upload
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.github.adizcode.cloudynotes.TopBar
import com.github.adizcode.cloudynotes.navigation.MainNavHost
import com.github.adizcode.cloudynotes.navigation.MainSubScreens
import com.github.adizcode.cloudynotes.ui.NotesViewModel
import com.github.adizcode.cloudynotes.ui.auth.CustomTextField
import com.github.adizcode.cloudynotes.ui.theme.Background
import com.github.adizcode.cloudynotes.ui.theme.SecondaryBackground
import kotlinx.coroutines.launch
import java.util.*

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainScaffold(viewModel: NotesViewModel, openFile: () -> Unit) {
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
                    .background(Color(0xFFD1EDBF))
                    .fillMaxWidth()
                    .fillMaxHeight(0.82f)
                    .padding(16.dp)
            ) {

                val (query, setQuery) = remember { mutableStateOf("") }
                Column(verticalArrangement = spacedBy(20.dp)) {
                    SearchView(query, setQuery)
                    ItemsList(query)
                }
            }
        }, sheetPeekHeight = 0.dp
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        val currentRoute = currentDestination?.route
        val isCurrentRouteNotNoteUpload =
            currentRoute == null || currentRoute != MainSubScreens.NoteUpload.route

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
                        navController.navigate(MainSubScreens.NoteUpload.route)
                    }, backgroundColor = Background) {
                        Icon(imageVector = Icons.Filled.FileUpload, contentDescription = null)
                    }
                }
            },
            bottomBar = {
                if (isCurrentRouteNotNoteUpload) {
                    BottomNavigation(backgroundColor = Background) {
                        MainSubScreens.bottomNavBarScreens.forEach { screen ->
                            BottomNavigationItem(
                                icon = { Icon(screen.imageVector, contentDescription = null) },
                                label = { Text(screen.label) },
                                selected = currentRoute?.equals(screen.route) ?: false,
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
                }
            },
        ) {
            MainNavHost(
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
fun HomeUi() {
    LazyColumn {
        item {
            MainSubScreenHeading("Fresh study material: ")
        }
        items(10) {
            Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                Spacer(modifier = Modifier.height(20.dp))
                Card(modifier = Modifier
                    .height(250.dp)
                    .clickable { }) {
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
fun MyNotesUi(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
    ) {
        MainSubScreenHeading("Your notes:")

        LazyColumn(verticalArrangement = spacedBy(20.dp)) {
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
fun NoteUploadUi(modifier: Modifier = Modifier, viewModel: NotesViewModel, openFile: () -> Unit) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = spacedBy(40.dp)
    ) {
        MainSubScreenHeading("Add a new note:")

        Column {

            CustomTextField(
                modifier = Modifier.fillMaxWidth(),
                value = viewModel.noteDescState.value,
                hint = "Give your note a descriptive name...",
                onValueChange = { viewModel.noteDescState.value = it },
            )

            Spacer(modifier = Modifier.height(15.dp))

            Button(modifier = Modifier.fillMaxWidth(), onClick = openFile) {
                Icon(Icons.Filled.Upload, null)
            }
        }

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = spacedBy(20.dp)) {
            OutlinedButton(modifier = Modifier.weight(0.5f), onClick = { /*TODO*/ }) {
                Text("Back")
            }
            Button(modifier = Modifier.weight(0.5f), onClick = viewModel::uploadNoteToStorage) {
                Text("Add Note")
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

@Composable
fun SearchView(query: String, setQuery: (String) -> Unit) {
    TextField(
        value = query,
        onValueChange = { value ->
            setQuery(value)
        },
        placeholder = { Text("Search", color = Color.DarkGray) },
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(15.dp)),
        textStyle = TextStyle(color = Color.Black, fontSize = 18.sp),
        leadingIcon = {
            Icon(
                tint = Color.Black,
                imageVector = Icons.Default.Search,
                contentDescription = "",
                modifier = Modifier
                    .padding(15.dp)
                    .size(24.dp),
            )
        },
        trailingIcon = {
            if (query != "") {
                IconButton(
                    onClick = {
                        setQuery("") // Remove text from TextField when you press the 'X' icon
                    }
                ) {
                    Icon(
                        tint = Color.Black,
                        imageVector = Icons.Default.Close,
                        contentDescription = "",
                        modifier = Modifier
                            .padding(15.dp)
                            .size(24.dp)
                    )
                }
            }
        },
        singleLine = true,
        shape = RectangleShape, // The TextFiled has rounded corners top left and right by default
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color.Black,
            cursorColor = Color.Black,
            leadingIconColor = Color.Black,
            trailingIconColor = Color.Black,
            backgroundColor = SecondaryBackground,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        )
    )
}


@Composable
fun ItemsList(query: String) {
    val items by remember { mutableStateOf(listOf("Drink water", "Walk")) }

    var filteredItems: List<String>
    LazyColumn(modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(15.dp))) {
        filteredItems = if (query.isEmpty()) {
            items
        } else {
            val resultList = ArrayList<String>()
            for (item in items) {
                if (item.lowercase(Locale.getDefault())
                        .contains(query.lowercase(Locale.getDefault()))
                ) {
                    resultList.add(item)
                }
            }
            resultList
        }
        items(filteredItems) { filteredItem ->
            ItemListItem(
                ItemText = filteredItem,
                onItemClick = { /*Click event code needs to be implement*/
                }
            )
        }

    }
}


@Composable
fun ItemListItem(ItemText: String, onItemClick: (String) -> Unit) {
    Row(
        modifier = Modifier
            .clickable(onClick = { onItemClick(ItemText) })
            .background(SecondaryBackground)
            .height(57.dp)
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(text = ItemText, fontSize = 18.sp)
    }
}