package com.github.adizcode.cloudynotes.ui.view.core.subscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.adizcode.cloudynotes.ui.view.common.CoreSubScreenHeading

@Composable
fun MyNotesSubScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
    ) {
        CoreSubScreenHeading(text = "Your notes:")

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