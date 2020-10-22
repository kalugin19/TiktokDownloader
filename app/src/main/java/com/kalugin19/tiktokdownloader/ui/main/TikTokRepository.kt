package com.kalugin19.tiktokdownloader.ui.main

import android.app.DownloadManager
import androidx.lifecycle.MutableLiveData

class TikTokRepository(private val api: TikTokApi = TikTokApiImpl()) {

    val downloadingRequestLiveData: MutableLiveData<Result<DownloadManager.Request>> = MutableLiveData()

    suspend fun download(url: String) {
        val result =
            try {
                val file = api.prepareDownloadingRequest(url)
                Result.success(file)
            } catch (e: Exception) {
                Result.failure(e)
            }
        downloadingRequestLiveData.postValue(result)
    }

}