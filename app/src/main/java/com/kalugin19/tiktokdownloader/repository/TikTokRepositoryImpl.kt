package com.kalugin19.tiktokdownloader.repository

import androidx.lifecycle.MutableLiveData
import com.kalugin19.tiktokdownloader.api.TikTokApi
import com.kalugin19.tiktokdownloader.data.ApiResult
import com.kalugin19.tiktokdownloader.data.Video
import com.kalugin19.tiktokdownloader.util.SingleLiveEvent

class TikTokRepositoryImpl(private val tikTokApi: TikTokApi) : TikTokRepository {

    override val downloadingRequestLiveData: MutableLiveData<ApiResult<Video>?> = SingleLiveEvent()

    override suspend fun download(url: String) {
        val apiResult = ApiResult<Video>()
        downloadingRequestLiveData.postValue(apiResult)
        tikTokApi.download(
                url = url,
                onResult = {
                    val result = Result.success(it)

                    apiResult.setResult(result)
                    downloadingRequestLiveData.postValue(apiResult)
                },
                onUpdate = { progress ->
                    apiResult.progress = progress
                    downloadingRequestLiveData.postValue(apiResult)
                }
        )
    }


}