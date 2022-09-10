package com.github.adizcode.cloudynotes.data.model

import android.net.Uri

data class UserNote(
    val desc: String,
    val downloadUrl: Uri,
    val isPublic: Boolean,
)
