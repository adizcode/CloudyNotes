package com.github.adizcode.cloudynotes.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import com.github.adizcode.cloudynotes.ui.navigation.MyAppNavHost
import com.github.adizcode.cloudynotes.ui.theme.CloudyNotesTheme
import com.github.adizcode.cloudynotes.ui.viewmodel.NoteUploadViewModel


// Request code for selecting a PDF document.
const val PICK_FILE = 2

const val EMAIL_POSTFIX = "@cumail.in"

class MainActivity : ComponentActivity() {

    private val viewModel: NoteUploadViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            CloudyNotesTheme {

                MyAppNavHost(viewModel = viewModel) { openFile() }

            }
        }
    }

    override fun onActivityResult(
        requestCode: Int, resultCode: Int, resultData: Intent?,
    ) {
        super.onActivityResult(requestCode, resultCode, resultData)
        if (requestCode == PICK_FILE && resultCode == Activity.RESULT_OK
        ) {
            // The result data contains a URI for the document or directory that
            // the user selected.
            resultData?.data?.also(viewModel::updateSelectedNoteUri)
        }
    }

    private fun openFile() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "*/*"
        }

        ActivityCompat.startActivityForResult(this, intent, PICK_FILE, null)
    }
}