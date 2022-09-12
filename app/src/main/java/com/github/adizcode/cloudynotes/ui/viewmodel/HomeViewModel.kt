package com.github.adizcode.cloudynotes.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.adizcode.cloudynotes.data.model.UserNote

class HomeViewModel: ViewModel() {

    private val _notesFeed = MutableLiveData<List<UserNote>>()

    val notesFeed: LiveData<List<UserNote>>
        get() = _notesFeed

}