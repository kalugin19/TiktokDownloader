package com.kalugin19.tiktokdownloader.ui.main

import android.Manifest
import android.app.Application
import android.app.DownloadManager
import android.content.ClipboardManager
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.arch.core.util.Function
import androidx.lifecycle.*
import com.kalugin19.tiktokdownloader.SingleLiveEvent
import com.kalugin19.tiktokdownloader.TikTokDownloaderApplication
import com.kalugin19.tiktokdownloader.downloadManager
import com.kalugin19.tiktokdownloader.getText
import kotlinx.coroutines.*

class MainViewModel(private val context: Application
) : AndroidViewModel(context) {

    private val repository: TikTokRepository = TikTokRepository()

    lateinit var launcher: ActivityResultLauncher<String>

    val addressLiveData: MutableLiveData<String> = SingleLiveEvent()

    val showCancelBtnLiveData: LiveData<Boolean> = Transformations.map(addressLiveData) {
        !it.isNullOrEmpty()
    }

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

        addressLiveData.observeForever {
            launcher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
        successLiveData.observeForever {
            context.downloadManager.enqueue(it)
        }
    }

    fun handlePermissionsResult(isGranted: Boolean) {
        if (isGranted) {
            addressLiveData.value?.let { download(it) }
        } else {
            errorLiveData.value = "Permissions have to be granted"
        }
    }

    fun clearUrl() {
        addressLiveData.value = ""
    }

    fun paste() {
        val copiedText = context.getText()

        if (copiedText?.isNotBlank() == true) {
            addressLiveData.value = copiedText
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