package com.github.adizcode.notes2cloud9.ui.main

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
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FileUpload
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.adizcode.notes2cloud9.TopBar
import com.github.adizcode.notes2cloud9.ui.theme.SecondaryBackground

@Composable
fun HomeUi() {

}

@Composable
fun SearchUi() {

}

@Composable
fun ProfileUi() {

}

@Composable
fun MyNotes() {

    Scaffold(modifier = Modifier
        .background(SecondaryBackground)
        .padding(horizontal = 16.dp, vertical = 32.dp),
        backgroundColor = SecondaryBackground,
        topBar = {
            TopBar()
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {}) {
                Icon(imageVector = Icons.Filled.FileUpload, contentDescription = null)
            }
        }) {
        Column(
            Modifier
                .padding(it)
                .fillMaxSize(),
        ) {
            Spacer(modifier = Modifier.height(10.dp))
            Text("Your notes:", fontSize = 32.sp, fontWeight = FontWeight.ExtraBold)

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