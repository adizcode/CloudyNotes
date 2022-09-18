package com.github.adizcode.cloudynotes.ui.view.core.subscreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.adizcode.cloudynotes.data.model.UserNote
import com.github.adizcode.cloudynotes.ui.view.common.CoreSubScreenHeading
import com.github.adizcode.cloudynotes.ui.view.common.NoteCard
import com.github.adizcode.cloudynotes.ui.viewmodel.HomeViewModel

@Preview(showSystemUi = true)
@Composable
fun HomeSubScreen(modifier: Modifier = Modifier, viewModel: HomeViewModel = viewModel()) {
    val notesFeed by viewModel.notesFeed.observeAsState(emptyList())
    val verticalSpace = 20.dp

    LazyColumn(modifier = modifier, verticalArrangement = Arrangement.spacedBy(verticalSpace)) {
        item {
            CoreSubScreenHeading(
                modifier = Modifier.padding(top = verticalSpace),
                text = "Fresh study material: ",
            )
        }
        itemsIndexed(notesFeed) { index, feedNote ->
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                NoteCard(feedNote)

                if (index == notesFeed.lastIndex) {
                    Spacer(modifier = Modifier.height(verticalSpace))
                }
            }
        }
    }
}