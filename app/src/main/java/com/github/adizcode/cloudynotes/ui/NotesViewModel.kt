package com.github.adizcode.cloudynotes.ui

import android.app.Application
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import com.github.adizcode.cloudynotes.utils.FirebaseCollections.USERS
import com.github.adizcode.cloudynotes.utils.FirebaseCollections.USER_NOTES
import com.github.adizcode.cloudynotes.data.model.UserNote
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.component1
import com.google.firebase.storage.ktx.component2
import com.google.firebase.storage.ktx.storage

class NotesViewModel(application: Application) : AndroidViewModel(application) {

    private val auth: FirebaseAuth = Firebase.auth
    private val firestore: FirebaseFirestore = Firebase.firestore
    private val storage: FirebaseStorage = Firebase.storage

    val noteDescState = mutableStateOf("")

    private var selectedNoteUri: Uri? = null

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

    fun updateSelectedNoteUri(noteUri: Uri) {
        selectedNoteUri = noteUri
        Toast.makeText(getApplication(), "Note selected!", Toast.LENGTH_SHORT).show()
    }

    fun uploadNoteToStorage() {
        val noteUri = selectedNoteUri ?: return

        // Perform operations on the document using its URI.
        val storageRef = storage.reference
        val uploadTask = storageRef.child("notes/${noteUri.lastPathSegment}").putFile(noteUri)

        uploadTask.addOnProgressListener { (bytesTransferred, totalByteCount) ->
            val progress = (100.0 * bytesTransferred) / totalByteCount
            Log.d("Storage", "Upload is $progress% done")
        }.addOnPausedListener {
            Log.d("Storage", "Upload is paused")
        }.addOnFailureListener {
            // Handle unsuccessful uploads
        }.addOnSuccessListener {
            // Handle successful uploads on complete
            val ref = it.storage

            storeUserNoteInFirestore(ref, noteDescState.value)

        }
    }

    private fun storeUserNoteInFirestore(
        ref: StorageReference,
        desc: String,
        isPublic: Boolean = false,
    ) {


        val currentUid = auth.currentUser?.uid

        if (currentUid != null) {
            val userNotes = firestore.collection(USERS).document(currentUid).collection(USER_NOTES)

            ref.downloadUrl.addOnSuccessListener {
                userNotes.add(
                    UserNote(
                        desc = desc,
                        downloadUrl = it,
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