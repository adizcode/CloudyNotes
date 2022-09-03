package com.github.adizcode.notes2cloud9

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.github.adizcode.notes2cloud9.ui.theme.Background
import com.github.adizcode.notes2cloud9.ui.theme.Notes2Cloud9Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Notes2Cloud9Theme {

                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = "authentication") {
                    composable("authentication") { Authentication() }
                    composable("onboarding") { }
                    composable("home") { }
                    composable("myNotes") { }
                    composable("profile") { }
                }

            }
        }
    }
}

@Preview
@Composable
fun Authentication() {

    val (isLogin, setLogin) = remember { mutableStateOf(true) }

    if (isLogin) {
        Login()
    } else {
        Register()
    }
}

@Composable
fun Login() {
    val (name, setName) = remember { mutableStateOf("") }
    val (email, setEmail) = remember { mutableStateOf("") }
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

        OutlinedTextField(value = name, onValueChange = setName)
        OutlinedTextField(value = email, onValueChange = setEmail)
        OutlinedTextField(value = password, onValueChange = setPassword)

        OutlinedButton(onClick = { /*TODO*/ }) {
            Text("Create account")
        }

    }
}

@Composable
fun Register() {

}