package com.github.adizcode.cloudynotes.ui.view.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.adizcode.cloudynotes.ui.theme.Background
import com.github.adizcode.cloudynotes.ui.theme.TextBlack
import com.github.adizcode.cloudynotes.ui.view.common.CustomTextField
import com.github.adizcode.cloudynotes.ui.viewmodel.NotesViewModel

@Composable
fun LoginScreen(
    navigateToHome: () -> Unit,
    navigateToRegister: () -> Unit,
    viewModel: NotesViewModel,
) {
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
            CustomTextField(
                isPassword = true,
                value = password,
                onValueChange = setPassword,
                hint = "Password",
            )
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