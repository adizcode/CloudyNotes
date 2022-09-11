package com.github.adizcode.cloudynotes.ui.view.core.subscreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.github.adizcode.cloudynotes.ui.view.common.CoreSubScreenHeading
import com.github.adizcode.cloudynotes.ui.view.common.CoreSubScreenSubHeading
import com.github.adizcode.cloudynotes.ui.view.common.NoteCard

@Composable
fun MyNotesSubScreen(modifier: Modifier = Modifier) {
    val verticalSpace = 20.dp
    val dummyPublicList = (1..2).toList()
    val dummyPrivateList = (1..3).toList()

    LazyColumn(modifier = modifier,
        verticalArrangement = Arrangement.Absolute.spacedBy(verticalSpace)) {
        item {
            CoreSubScreenHeading(
                modifier = Modifier.padding(top = verticalSpace),
                text = "Your notes:",
            )
        }
        item {
            CoreSubScreenSubHeading(text = "Public Notes")
        }
        items(dummyPublicList) {
            NoteCard()
        }
        item {
            CoreSubScreenSubHeading(text = "Private Notes")
        }
        itemsIndexed(dummyPrivateList) { index, _ ->
            NoteCard()

            if (index == dummyPrivateList.lastIndex) {
                Spacer(modifier = Modifier.height(verticalSpace))
            }
        }
    }
}