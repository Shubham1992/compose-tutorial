package com.shubham.myfirstcomposeapp.launched_effect

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

//Viewmodel to test how composable launchedEffect works
class LaunchedEffectViewModel : ViewModel() {

    private val liveDataInternal = MutableLiveData<String>()
    val liveData: LiveData<String> = liveDataInternal

    init {
        liveDataInternal.postValue("init Hi...")
    }

    fun updateData() {
        liveDataInternal.postValue("#")
    }
}