package com.kalugin19.tiktokdownloader.ui.main

import android.Manifest
import android.app.Application
import androidx.activity.result.ActivityResultLauncher
import androidx.lifecycle.*
import com.kalugin19.tiktokdownloader.data.ApiResult
import com.kalugin19.tiktokdownloader.data.Video
import com.kalugin19.tiktokdownloader.repository.TikTokRepository
import com.kalugin19.tiktokdownloader.util.ServiceLocator
import com.kalugin19.tiktokdownloader.util.SingleLiveEvent
import com.kalugin19.tiktokdownloader.util.getText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(
    private val context: Application
) : AndroidViewModel(context) {

    private val repository: TikTokRepository = ServiceLocator.tikTokRepository

    lateinit var launcher: ActivityResultLauncher<String>

    val addressLiveData: MutableLiveData<String?> = SingleLiveEvent()

    val showCancelBtnLiveData: LiveData<Boolean> = Transformations.map(addressLiveData) {
        !it.isNullOrEmpty()
    }

    val progressLiveData: LiveData<Int> = MediatorLiveData<Int>().apply {
        value = 0
        addSource(repository.downloadingRequestLiveData) {
            if (it?.isProgress == true) {
                value = it.progress
            }
        }
    }

    private val _errorLiveData = MutableLiveData("")

    val errorMediatorLiveData: LiveData<String> = MediatorLiveData<String>().apply {
        val errorLiveData = repository.downloadingRequestLiveData
            .map {
                return@map if (it?.isFailure == true) {
                    it.getExceptionOrNull()?.message ?: "Unknown error"
                } else {
                    ""
                }
            }

        val observer = Observer<String> {
            value = it
        }

        addSource(errorLiveData, observer)
        addSource(_errorLiveData, observer)
    }

    private val _videoUrlLiveData: LiveData<String?> =
        MediatorLiveData<ApiResult<Video>?>()
            .apply {
                addSource(repository.downloadingRequestLiveData) {
                    if (it?.isSuccess == true) {
                        value = it
                    }
                }
            }
            .map {
                val pair = it?.getOrNull()
                pair?.url
            }


    val videoUrlLiveData = _videoUrlLiveData
            .distinctUntilChanged()
            .map {
        it ?: ""
    }

    init {
        addressLiveData.observeForever {
            if (it?.isNotEmpty() == true) {
                launcher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
        }
    }


    fun handlePermissionsResult(isGranted: Boolean) {
        if (isGranted) {
            addressLiveData.value?.let { download(it) }
        } else {
            _errorLiveData.value = "Permissions have to be granted"
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

    private suspend fun downloadAsync(url: String) = withContext(Dispatchers.IO) {
        repository.download(url)
    }

}