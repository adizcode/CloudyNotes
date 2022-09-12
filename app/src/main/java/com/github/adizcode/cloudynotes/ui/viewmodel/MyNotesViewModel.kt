package com.github.adizcode.cloudynotes.ui.viewmodel

import android.app.Application
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.adizcode.cloudynotes.data.model.UserNote
import com.github.adizcode.cloudynotes.utils.FirebaseCollections.USERS
import com.github.adizcode.cloudynotes.utils.FirebaseCollections.USER_NOTES
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

class MyNotesViewModel(application: Application) : AndroidViewModel(application) {

    companion object {
        private const val TAG = "MyNotesViewModel"
    }

    private val firestore: FirebaseFirestore = Firebase.firestore
    private val storage: FirebaseStorage = Firebase.storage
    private val auth: FirebaseAuth = Firebase.auth

    private val _myPublicNotes = MutableLiveData<List<UserNote>>()

    val myPublicNotes: LiveData<List<UserNote>>
        get() = _myPublicNotes

    private val _myPrivateNotes = MutableLiveData<List<UserNote>>()

    val myPrivateNotes: LiveData<List<UserNote>>
        get() = _myPrivateNotes

    val noteDescState = mutableStateOf("")

    private var selectedNoteUri: Uri? = null

    init {
        fetchUserNotes()
    }

    private fun fetchUserNotes() {

        val currentUid = getCurrentUid()
        if (currentUid != null) {

            val userNotesCollection =
                firestore.collection(USERS).document(currentUid).collection(USER_NOTES)

            userNotesCollection.whereEqualTo("public", true)
                .addSnapshotListener { value, error ->
                    if (error != null) {
                        Log.e(TAG, "Public Notes Listener failed", error)
                        return@addSnapshotListener
                    }

                    _myPublicNotes.value =
                        value?.documents?.map { UserNote.createFromDocMap(it.data) }
                }

            userNotesCollection.whereEqualTo("public", false)
                .addSnapshotListener { value, error ->
                    if (error != null) {
                        Log.e(TAG, "Private Notes Listener failed", error)
                        return@addSnapshotListener
                    }

                    _myPrivateNotes.value =
                        value?.documents?.map { UserNote.createFromDocMap(it.data) }
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


        val currentUid = getCurrentUid()

        if (currentUid != null) {
            val userNotes = firestore.collection(USERS).document(currentUid).collection(USER_NOTES)

            ref.downloadUrl.addOnSuccessListener {
                userNotes.add(
                    UserNote(
                        desc = desc,
                        downloadUrl = it,
                        public = isPublic,
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

    private fun getCurrentUid() = auth.currentUser?.uid
}