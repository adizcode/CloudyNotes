package com.github.adizcode.cloudynotes.ui.viewmodel

import android.app.Application
import android.util.Log
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

class MyNotesViewModel(application: Application) : AndroidViewModel(application) {

    companion object {
        private const val TAG = "MyNotesViewModel"
    }

    private val firestore: FirebaseFirestore = Firebase.firestore
    private val auth: FirebaseAuth = Firebase.auth

    private val _myPublicNotes = MutableLiveData<List<UserNote>>()

    val myPublicNotes: LiveData<List<UserNote>>
        get() = _myPublicNotes

    private val _myPrivateNotes = MutableLiveData<List<UserNote>>()

    val myPrivateNotes: LiveData<List<UserNote>>
        get() = _myPrivateNotes

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

    private fun getCurrentUid() = auth.currentUser?.uid
}