package com.github.adizcode.cloudynotes.ui

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import com.github.adizcode.cloudynotes.EMAIL_POSTFIX
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class NotesViewModel(application: Application) : AndroidViewModel(application) {

    private var auth: FirebaseAuth = Firebase.auth

    fun login(uid: String, password: String, navigateToHome: () -> Unit) {

        if (uid.isNotBlank() && password.isNotBlank()) {
            auth.signInWithEmailAndPassword(uid.trim() + EMAIL_POSTFIX, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        val user = auth.currentUser
                        navigateToHome()
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(getApplication(), "Error: Please make sure to enter the correct details.",
                            Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

    fun register(uid: String, password: String, navigateToHome: () -> Unit) {
        if (uid.isNotBlank() && password.isNotBlank()) {
            auth.createUserWithEmailAndPassword(uid.trim() + EMAIL_POSTFIX, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        val user = auth.currentUser
                        navigateToHome()
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(getApplication(), "Error: Please make sure to enter the correct details.",
                            Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
}