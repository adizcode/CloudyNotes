package com.github.adizcode.cloudynotes.ui

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import com.github.adizcode.cloudynotes.EMAIL_POSTFIX
import com.github.adizcode.cloudynotes.FirebaseCollections.USERS
import com.github.adizcode.cloudynotes.FirebaseCollections.USER_NOTES
import com.github.adizcode.cloudynotes.UserNote
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference

class NotesViewModel(application: Application) : AndroidViewModel(application) {

    private val auth: FirebaseAuth = Firebase.auth
    private val firestore: FirebaseFirestore = Firebase.firestore

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
                        Toast.makeText(getApplication(),
                            "Error: Please make sure to enter the correct details.",
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
                        Toast.makeText(getApplication(),
                            "Error: Please make sure to enter the correct details.",
                            Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

    fun storeUserNote(ref: StorageReference, desc: String, isPublic: Boolean = false) {


        val currentUid = auth.currentUser?.uid

        if (currentUid != null) {
            val userNotes = firestore.collection(USERS).document(currentUid).collection(USER_NOTES)

            ref.downloadUrl.addOnSuccessListener {
                userNotes.add(
                    UserNote(
                        desc = desc,
                        downloadUri = it,
                        isPublic = isPublic,
                    ),
                ).addOnSuccessListener {
                    Toast.makeText(getApplication(), "Note has been added!", Toast.LENGTH_SHORT)
                        .show()
                }.addOnFailureListener {
                    Toast.makeText(getApplication(),
                        "An error has occurred",
                        Toast.LENGTH_SHORT).show()
                }
            }.addOnFailureListener {
                Toast.makeText(getApplication(),
                    "An error has occurred",
                    Toast.LENGTH_SHORT).show()
            }

        }
    }
}