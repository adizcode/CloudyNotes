package com.github.adizcode.cloudynotes

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import coil.compose.AsyncImage
import com.github.adizcode.cloudynotes.navigation.MyAppNavHost
import com.github.adizcode.cloudynotes.ui.NotesViewModel
import com.github.adizcode.cloudynotes.ui.theme.CloudyNotesTheme


// Request code for selecting a PDF document.
const val PICK_FILE = 2

const val EMAIL_POSTFIX = "@cumail.in"

class MainActivity : ComponentActivity() {

    private val viewModel: NotesViewModel by viewModels()

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

@Composable
fun TopBar(modifier: Modifier = Modifier) {

    Row(modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = spacedBy(10.dp),
        ) {
            AsyncImage(
                model = "https://4dhealth.ca/wp-content/uploads/2021/04/happy.jpg",
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .border(4.dp, Color.White, CircleShape)
                    .clickable { },
            )
            Text("Hi, how are you?", fontSize = 18.sp, fontWeight = FontWeight.Medium)
        }
        IconButton(onClick = { /*TODO*/ }) {
            Icon(modifier = Modifier.size(28.dp),
                imageVector = Icons.Filled.Notifications,
                contentDescription = null)
        }
    }
}