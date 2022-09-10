package com.github.adizcode.cloudynotes.ui.view.main.subscreens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Upload
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.github.adizcode.cloudynotes.ui.NotesViewModel
import com.github.adizcode.cloudynotes.ui.view.common.CustomTextField
import com.github.adizcode.cloudynotes.ui.view.common.MainSubScreenHeading

@Composable
fun NoteUploadSubScreen(
    modifier: Modifier = Modifier,
    viewModel: NotesViewModel,
    openFile: () -> Unit,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Absolute.spacedBy(40.dp)
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

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Absolute.spacedBy(20.dp)) {
            OutlinedButton(modifier = Modifier.weight(0.5f), onClick = { /*TODO*/ }) {
                Text("Back")
            }
            Button(modifier = Modifier.weight(0.5f), onClick = viewModel::uploadNoteToStorage) {
                Text("Add Note")
            }
        }
    }
}