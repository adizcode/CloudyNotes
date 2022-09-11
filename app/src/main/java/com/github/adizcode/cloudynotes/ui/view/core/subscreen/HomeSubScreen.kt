package com.github.adizcode.cloudynotes.ui.view.core.subscreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.github.adizcode.cloudynotes.ui.view.common.CoreSubScreenHeading

@Composable
fun HomeSubScreen(modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier) {
        item {
            CoreSubScreenHeading("Fresh study material: ")
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