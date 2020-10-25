package com.kalugin19.tiktokdownloader.ui.main

import androidx.lifecycle.MutableLiveData

class TikTokRepository(private val api: TikTokApi = TikTokApiImpl()) {

    val downloadingRequestLiveData: MutableLiveData<Result<Pair<String, String>>> = MutableLiveData()

    suspend fun download(url: String) {
        val result =
            try {
                val pair = api.getDownloadingUrl(url)
                Result.success(pair)
            } catch (e: Exception) {
                Result.failure(e)
            }
        downloadingRequestLiveData.postValue(result)
    }

}