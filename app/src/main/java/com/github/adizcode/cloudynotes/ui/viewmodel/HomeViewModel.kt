package com.github.adizcode.cloudynotes.ui.viewmodel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.github.adizcode.cloudynotes.data.model.UserNote
import com.github.adizcode.cloudynotes.utils.FirebaseCollections.USERS
import com.github.adizcode.cloudynotes.utils.FirebaseCollections.USER_NOTES
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    companion object {
        private const val TAG = "HomeViewModel"
    }

    private val firestore: FirebaseFirestore = Firebase.firestore

    private val _notesFeed = MutableLiveData<List<UserNote>>()

    val notesFeed: LiveData<List<UserNote>>
        get() = _notesFeed

    init {
        fetchNotesFeed()
    }

    private fun fetchNotesFeed() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val notesFeedList = mutableListOf<UserNote>()
                val allUsersQuery = firestore.collection(USERS).get().await()

                withContext(Dispatchers.Main) {
                    val firstDocId = allUsersQuery.documents[0].id
                    Log.d(TAG, "First doc's ID = $firstDocId")
                }

                for (userDoc in allUsersQuery) {
                    val userPublicNotesQuery =
                        firestore.collection(USERS).document(userDoc.id).collection(USER_NOTES)
                            .whereEqualTo("public", true)

                    val userPublicNotes =
                        userPublicNotesQuery.get().await()
                            .map { UserNote.createFromDocMap(it.data) }

                    notesFeedList.addAll(userPublicNotes)
                }

                withContext(Dispatchers.Default) {
                    notesFeedList.sortBy(UserNote::timeStamp)
                    _notesFeed.postValue(notesFeedList)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(getApplication(),
                        "Error while loading notes feed = $e",
                        Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}