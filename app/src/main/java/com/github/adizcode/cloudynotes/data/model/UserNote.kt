package com.github.adizcode.cloudynotes.data.model

import android.net.Uri
import com.google.firebase.Timestamp
import com.google.firebase.firestore.ServerTimestamp


data class UserNote(
    val desc: String = "Default Description",
    val downloadUrl: Uri = Uri.EMPTY,
    val public: Boolean = false,
    @ServerTimestamp
    val timeStamp: Timestamp? = null,
) {
    companion object {
        fun createFromDocMap(docMap: Map<String, Any>?): UserNote {
            if (docMap == null) return UserNote()
            return UserNote(
                desc = docMap["desc"] as String,
                downloadUrl = Uri.parse(docMap["downloadUrl"] as String),
                public = docMap["public"] as Boolean,
                timeStamp = docMap["timeStamp"] as Timestamp
            )
        }
    }
}
