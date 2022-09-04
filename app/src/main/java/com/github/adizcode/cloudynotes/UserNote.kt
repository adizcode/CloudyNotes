package com.github.adizcode.cloudynotes

import android.net.Uri

data class UserNote(
    val desc: String,
    val downloadUri: Uri,
    val isPublic: Boolean,
)
