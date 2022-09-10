package com.github.adizcode.cloudynotes.ui.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.adizcode.cloudynotes.ui.NotesViewModel
import com.github.adizcode.cloudynotes.ui.theme.Background
import com.github.adizcode.cloudynotes.ui.theme.TextBlack

@Composable
fun Login(navigateToHome: () -> Unit, navigateToRegister: () -> Unit, viewModel: NotesViewModel) {
    val (uid, setUid) = remember { mutableStateOf("") }
    val (password, setPassword) = remember { mutableStateOf("") }

    Column(
        Modifier
            .fillMaxSize()
            .background(Background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text("Sign In", fontWeight = FontWeight.ExtraBold, fontSize = 26.sp)
        Text("Let's get back to sharing notes")

        Spacer(modifier = Modifier.height(20.dp))

        Column(verticalArrangement = Arrangement.Absolute.spacedBy(20.dp)) {
            CustomTextField(value = uid, onValueChange = setUid, hint = "UID")
            CustomTextField(isPassword = true,
                value = password,
                onValueChange = setPassword,
                hint = "Password")
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = { viewModel.login(uid, password) { navigateToHome() } },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = TextBlack,
                contentColor = Background,
            )) {
            Text("Login")
        }

        Spacer(modifier = Modifier.height(10.dp))

        Button(onClick = navigateToRegister,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = TextBlack,
                contentColor = Background,
            )) {
            Text("Sign up for a new account?")
        }

    }
}

@Composable
fun Register(navigateToHome: () -> Unit, navigateToLogin: () -> Unit, viewModel: NotesViewModel) {

    val (uid, setUid) = remember { mutableStateOf("") }
    val (password, setPassword) = remember { mutableStateOf("") }

    Column(
        Modifier
            .fillMaxSize()
            .background(Background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text("Create an account", fontWeight = FontWeight.ExtraBold, fontSize = 26.sp)
        Text("Let's get started sharing notes")

        Spacer(modifier = Modifier.height(20.dp))

        Column(verticalArrangement = Arrangement.Absolute.spacedBy(20.dp)) {
            CustomTextField(value = uid, onValueChange = setUid, hint = "UID")
            CustomTextField(isPassword = true,
                value = password,
                onValueChange = setPassword,
                hint = "Password")
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = { viewModel.register(uid, password) { navigateToHome() } },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = TextBlack,
                contentColor = Background,
            )) {
            Text("Create account")
        }

        Spacer(modifier = Modifier.height(10.dp))

        Button(onClick = navigateToLogin,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = TextBlack,
                contentColor = Background,
            )) {
            Text("Already have an account?")
        }
    }
}

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