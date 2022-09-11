package com.github.adizcode.cloudynotes.ui.view.core.subscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.adizcode.cloudynotes.ui.theme.SecondaryBackground
import java.util.*

@Composable
fun SearchBottomSheet(modifier: Modifier = Modifier) {
    Box(
        modifier
            .background(Color(0xFFD1EDBF))
            .fillMaxWidth()
            .fillMaxHeight(0.82f)
            .padding(16.dp)
    ) {

        val (query, setQuery) = remember { mutableStateOf("") }
        Column(verticalArrangement = Arrangement.Absolute.spacedBy(20.dp)) {
            SearchBox(query, setQuery)
            SearchItemsList(query)
        }
    }
}

@Composable
fun SearchBox(query: String, setQuery: (String) -> Unit) {
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
fun SearchItemsList(query: String) {
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
            SearchItem(
                ItemText = filteredItem,
                onItemClick = { /*Click event code needs to be implement*/
                }
            )
        }

    }
}


@Composable
fun SearchItem(ItemText: String, onItemClick: (String) -> Unit) {
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