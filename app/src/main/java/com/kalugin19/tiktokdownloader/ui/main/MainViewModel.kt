package com.kalugin19.tiktokdownloader.ui.main

import android.Manifest
import android.app.Application
import android.app.DownloadManager
import android.net.Uri
import android.os.Environment
import androidx.activity.result.ActivityResultLauncher
import androidx.lifecycle.*
import com.kalugin19.tiktokdownloader.util.SingleLiveEvent
import com.kalugin19.tiktokdownloader.util.downloadManager
import com.kalugin19.tiktokdownloader.util.getText
import kotlinx.coroutines.*

class MainViewModel(private val context: Application
) : AndroidViewModel(context) {

    private val repository: TikTokRepository = TikTokRepository()

    lateinit var launcher: ActivityResultLauncher<String>

    val addressLiveData: MutableLiveData<String> = SingleLiveEvent()

    val showCancelBtnLiveData: LiveData<Boolean> = Transformations.map(addressLiveData) {
        !it.isNullOrEmpty()
    }

    val videoUrlLiveData: MutableLiveData<String> = SingleLiveEvent()

    val errorLiveData = MutableLiveData<String>()
    private val successLiveData = MutableLiveData<DownloadManager.Request>()


    init {
        repository.downloadingRequestLiveData.observeForever {
            if (it.isFailure) {
                errorLiveData.value = it.exceptionOrNull()?.message ?: "Unknown error"
            } else {
                val pair = it.getOrNull()
                pair?.apply {
                    val title = first
                    val url = second
                    videoUrlLiveData.value = url
                    val downloadingRequest = prepareDownloadingRequest(title, url)
                    successLiveData.value = downloadingRequest
                }
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


    private fun download(url: String) {
        viewModelScope.launch {
            downloadAsync(url)
        }
    }

    private fun prepareDownloadingRequest(title: String, downloadUrl: String): DownloadManager.Request {
        return DownloadManager.Request(Uri.parse(downloadUrl))
                .setTitle(title)
                .setDescription("Downloading...")
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setDestinationInExternalPublicDir(
                        Environment.DIRECTORY_DOWNLOADS, title
                )
    }

    private suspend fun downloadAsync(url: String) = withContext(Dispatchers.IO) {
        repository.download(url)
    }

}