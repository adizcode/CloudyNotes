package com.github.adizcode.notes2cloud9

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.github.adizcode.notes2cloud9.ui.theme.Background
import com.github.adizcode.notes2cloud9.ui.theme.Notes2Cloud9Theme
import com.github.adizcode.notes2cloud9.ui.theme.SecondaryBackground
import com.github.adizcode.notes2cloud9.ui.theme.TextBlack

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Notes2Cloud9Theme {

                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = "authentication") {
                    composable("authentication") { Authentication { navController.navigate("home") } }
                    composable("home") { Home() }
                    composable("myNotes") { }
                    composable("profile") { }
                }

            }
        }
    }
}

@Composable
fun Authentication(goToHome: () -> Unit) {

    val (isLogin, setLogin) = remember { mutableStateOf(true) }

    if (isLogin) {
        Login(goToHome) { setLogin(false) }
    } else {
        Register(goToHome) { setLogin(true) }
    }
}

@Composable
fun Home() {
    Column(
        Modifier
            .fillMaxSize()
            .background(SecondaryBackground)
            .padding(horizontal = 16.dp, vertical = 32.dp),
    ) {
        TopBar()
        Spacer(modifier = Modifier.height(20.dp))
        Text("Notes for you:", fontSize = 28.sp, fontWeight = FontWeight.ExtraBold)

    }
}

@Composable
fun TopBar() {
    Row(modifier = Modifier.fillMaxWidth(),
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
                    .size(40.dp)
                    .clip(CircleShape),
            )
            Text("Hi, Kira!")
        }
        IconButton(onClick = { /*TODO*/ }) {
            Icon(modifier = Modifier.size(28.dp),
                imageVector = Icons.Filled.Notifications,
                contentDescription = null)
        }
    }
}

@Composable
fun Login(goToHome: () -> Unit, goToRegister: () -> Unit) {
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

        Column(verticalArrangement = spacedBy(20.dp)) {
            CustomTextField(value = uid, onValueChange = setUid, hint = "UID")
            CustomTextField(value = password, onValueChange = setPassword, hint = "Password")
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = goToHome,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = TextBlack,
                contentColor = Background,
            )) {
            Text("Login")
        }

        Spacer(modifier = Modifier.height(10.dp))

        Button(onClick = goToRegister,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = TextBlack,
                contentColor = Background,
            )) {
            Text("Sign up for a new account?")
        }

    }
}

@Composable
fun Register(goToHome: () -> Unit, goToLogin: () -> Unit) {

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

        Column(verticalArrangement = spacedBy(20.dp)) {
            CustomTextField(value = uid, onValueChange = setUid, hint = "UID")
            CustomTextField(value = password, onValueChange = setPassword, hint = "Password")
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = goToHome,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = TextBlack,
                contentColor = Background,
            )) {
            Text("Create account")
        }

        Spacer(modifier = Modifier.height(10.dp))

        Button(onClick = goToLogin,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = TextBlack,
                contentColor = Background,
            )) {
            Text("Already have an account?")
        }
    }
}

@Composable
fun CustomTextField(value: String, hint: String, onValueChange: (String) -> Unit) {
    Card {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(hint) },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                disabledBorderColor = Color.Transparent,
                errorBorderColor = Color.Transparent,
            ),
        )
    }
}