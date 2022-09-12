package com.github.adizcode.cloudynotes.ui.view.core.subscreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.adizcode.cloudynotes.ui.view.common.CoreSubScreenHeading
import com.github.adizcode.cloudynotes.ui.view.common.CoreSubScreenSubHeading
import com.github.adizcode.cloudynotes.ui.view.common.NoteCard
import com.github.adizcode.cloudynotes.ui.viewmodel.MyNotesViewModel

@Composable
fun MyNotesSubScreen(modifier: Modifier = Modifier, viewModel: MyNotesViewModel = viewModel()) {
    val verticalSpace = 20.dp
    val publicNotes by viewModel.myPublicNotes.observeAsState(emptyList())
    val privateNotes by viewModel.myPrivateNotes.observeAsState(emptyList())

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
        items(publicNotes) {
            NoteCard()
        }
        item {
            CoreSubScreenSubHeading(text = "Private Notes")
        }
        itemsIndexed(privateNotes) { index, _ ->
            NoteCard()

            if (index == privateNotes.lastIndex) {
                Spacer(modifier = Modifier.height(verticalSpace))
            }
        }
    }
}