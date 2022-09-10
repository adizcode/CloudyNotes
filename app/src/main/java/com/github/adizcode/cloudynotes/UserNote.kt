package com.github.adizcode.cloudynotes

import android.net.Uri

data class UserNote(
    val desc: String,
    val downloadUrl: Uri,
    val isPublic: Boolean,
)
