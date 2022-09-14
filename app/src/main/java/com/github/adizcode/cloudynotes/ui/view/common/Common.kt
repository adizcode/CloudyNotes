package com.github.adizcode.cloudynotes.ui.view.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.github.adizcode.cloudynotes.ui.theme.Background
import com.github.adizcode.cloudynotes.ui.theme.TextBlack

@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    isPassword: Boolean = false,
    value: String,
    hint: String,
    onValueChange: (String) -> Unit,
) {
    Card(modifier = modifier) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(hint) },
            visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
            keyboardOptions = KeyboardOptions(keyboardType = if (isPassword) KeyboardType.Password else KeyboardType.Text),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                disabledBorderColor = Color.Transparent,
                errorBorderColor = Color.Transparent,
            ),
        )
    }
}

@Composable
fun CoreSubScreenHeading(modifier: Modifier = Modifier, text: String) {
    Text(
        modifier = modifier,
        text = text,
        fontSize = 32.sp,
        fontWeight = FontWeight.ExtraBold,
    )
}

@Composable
fun CoreSubScreenSubHeading(modifier: Modifier = Modifier, text: String) {
    Text(modifier = modifier, text = text, fontSize = 22.sp, fontWeight = FontWeight.Bold)
}

@Composable
fun NoteCard() {
    Card(
        modifier = Modifier
            .height(250.dp)
            .clickable { },
    ) {
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

@Composable
fun LabelledCheckBox(
    modifier: Modifier = Modifier,
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit),
    label: String,
) {
    Row(
        modifier = modifier
            .clip(MaterialTheme.shapes.small)
            .clickable(
                indication = rememberRipple(color = Color.Green),
                interactionSource = remember { MutableInteractionSource() },
                onClick = { onCheckedChange(!checked) }
            )
            .requiredHeight(ButtonDefaults.MinHeight)
            .padding(4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {

        Text(
            text = label,
            fontWeight = FontWeight.Medium
        )

        Checkbox(
            checked = checked,
            onCheckedChange = null,
            colors = CheckboxDefaults.colors(checkedColor = Background, )
        )
    }
}