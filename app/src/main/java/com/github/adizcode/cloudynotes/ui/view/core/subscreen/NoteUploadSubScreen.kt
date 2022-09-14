package com.github.adizcode.cloudynotes.ui.view.core.subscreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Upload
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.adizcode.cloudynotes.ui.theme.Background
import com.github.adizcode.cloudynotes.ui.theme.TextBlack
import com.github.adizcode.cloudynotes.ui.view.common.CoreSubScreenHeading
import com.github.adizcode.cloudynotes.ui.view.common.CustomTextField
import com.github.adizcode.cloudynotes.ui.view.common.LabelledCheckBox
import com.github.adizcode.cloudynotes.ui.viewmodel.NoteUploadViewModel

@Composable
fun NoteUploadSubScreen(
    modifier: Modifier = Modifier,
    viewModel: NoteUploadViewModel = viewModel(),
    openFile: () -> Unit,
    goBack: () -> Unit,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(40.dp)
    ) {
        CoreSubScreenHeading(modifier = Modifier.padding(top = 20.dp), text = "Add a new note:")

        Column {

            CustomTextField(
                modifier = Modifier.fillMaxWidth(),
                value = viewModel.noteDescState.value,
                hint = "Give your note a descriptive name...",
                onValueChange = { viewModel.noteDescState.value = it },
            )

            Spacer(modifier = Modifier.height(15.dp))

            Button(
                modifier = Modifier.fillMaxWidth(), onClick = openFile,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Background,
                    contentColor = TextBlack,
                ),
            ) {
                Icon(Icons.Filled.Upload, null)
            }
        }

        LabelledCheckBox(
            modifier = Modifier.fillMaxWidth(),
            checked = viewModel.notePublicState.value,
            onCheckedChange = { viewModel.notePublicState.value = it },
            label = "Make note public",
        )

        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Absolute.spacedBy(20.dp)) {
            OutlinedButton(
                modifier = Modifier.weight(0.5f), onClick = goBack,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.White,
                    contentColor = TextBlack,
                ),
            ) {
                Text("Back")
            }
            Button(
                modifier = Modifier.weight(0.5f), onClick = viewModel::uploadNoteToStorage,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Background,
                    contentColor = TextBlack,
                ),
            ) {
                Text("Add Note")
            }
        }
    }
}