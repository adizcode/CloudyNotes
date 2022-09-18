package com.github.adizcode.cloudynotes.ui.viewmodel

import android.app.Application
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import com.github.adizcode.cloudynotes.data.model.UserNote
import com.github.adizcode.cloudynotes.utils.FirebaseCollections
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

class NoteUploadViewModel(application: Application) : AndroidViewModel(application) {

    companion object {
        private const val TAG = "NoteUploadViewModel"
    }

    private val firestore: FirebaseFirestore = Firebase.firestore
    private val storage: FirebaseStorage = Firebase.storage
    private val auth: FirebaseAuth = Firebase.auth

    val noteDescState = mutableStateOf("")
    val notePublicState = mutableStateOf(true)

    private var selectedNoteUri: Uri? = null

    fun updateSelectedNoteUri(noteUri: Uri) {
        selectedNoteUri = noteUri
        Toast.makeText(getApplication(), "Note selected!", Toast.LENGTH_SHORT).show()
    }

    fun addNewNote(runAfterUserNoteAdded: () -> Unit) {
        uploadNoteToStorage(runAfterUserNoteAdded)
    }

    private fun uploadNoteToStorage(runAfterUserNoteStored: () -> Unit) {
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

            storeUserNoteInFirestore(
                ref = ref,
                desc = noteDescState.value,
                isPublic = notePublicState.value,
                runAfterUserNoteStored = runAfterUserNoteStored,
            )

        }
    }

    private fun storeUserNoteInFirestore(
        ref: StorageReference,
        desc: String,
        isPublic: Boolean,
        runAfterUserNoteStored: () -> Unit,
    ) {

        val currentUid = getCurrentUid()

        if (currentUid != null) {

            val userDoc = firestore.collection(FirebaseCollections.USERS).document(currentUid)

            userDoc.set("isDummyField" to true)
                .addOnSuccessListener {

                    val userNotes =
                        userDoc.collection(
                            FirebaseCollections.USER_NOTES)

                    ref.downloadUrl.addOnSuccessListener {
                        val newNote = UserNote(
                            desc = desc,
                            downloadUrl = it,
                            public = isPublic,
                        )

                        userNotes.add(newNote).addOnSuccessListener {
                            Toast.makeText(getApplication(),
                                "Note has been added!",
                                Toast.LENGTH_SHORT)
                                .show()
                            runAfterUserNoteStored()
                            resetNewNoteState()

                        }.addOnFailureListener {
                            Log.e(TAG, "Error while adding note in firestore")
                            Toast.makeText(getApplication(),
                                "An error has occurred",
                                Toast.LENGTH_SHORT).show()
                        }

                    }.addOnFailureListener {
                        Log.e(TAG, "Error while getting download url")
                        Toast.makeText(getApplication(),
                            "An error has occurred",
                            Toast.LENGTH_SHORT).show()
                    }
                }.addOnFailureListener {
                    Log.e(TAG, "Error while setting dummy field in user doc")
                    Toast.makeText(getApplication(),
                        "An error has occurred",
                        Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun resetNewNoteState() {
        noteDescState.value = ""
        notePublicState.value = true
        selectedNoteUri = null
    }

    private fun getCurrentUid() = auth.currentUser?.uid
}