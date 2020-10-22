package com.kalugin19.tiktokdownloader.ui.main

import android.app.Application
import android.app.DownloadManager
import androidx.lifecycle.*
import kotlinx.coroutines.*

class MainViewModel(context: Application) : AndroidViewModel(context) {

    private val repository: TikTokRepository = TikTokRepository()

    val errorLiveData = MutableLiveData<String>()
    val successLiveData = MutableLiveData<DownloadManager.Request>()

    init {
        repository.downloadingRequestLiveData.observeForever {
            if (it.isFailure) {
                errorLiveData.value = it.exceptionOrNull()?.message ?: "Unknown error"
            } else {
                successLiveData.value = it.getOrNull()
            }
        }
    }

    fun download(url: String) {
        viewModelScope.launch {
            downloadAsync(url)
        }
    }

    private suspend fun downloadAsync(url: String) = withContext(Dispatchers.IO) {
        repository.download(url)
    }

}