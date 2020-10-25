package com.kalugin19.tiktokdownloader.repository

import androidx.lifecycle.MutableLiveData
import com.kalugin19.tiktokdownloader.api.TikTokApi
import com.kalugin19.tiktokdownloader.data.ApiResult
import com.kalugin19.tiktokdownloader.data.Video

class TikTokRepositoryImpl(private val tikTokApi: TikTokApi) : TikTokRepository {

    override val downloadingRequestLiveData: MutableLiveData<ApiResult<Video>?> = MutableLiveData()

    override suspend fun download(url: String) {
        val apiResult = ApiResult<Video>()
        val result =
            try {
                val video = tikTokApi.download(url) { progress ->
                    apiResult.progress = progress
                    downloadingRequestLiveData.postValue(apiResult)
                }
                Result.success(video)
            } catch (e: Exception) {
                Result.failure(e)
            }
        apiResult.setResult(result)
        downloadingRequestLiveData.postValue(apiResult)
    }


}