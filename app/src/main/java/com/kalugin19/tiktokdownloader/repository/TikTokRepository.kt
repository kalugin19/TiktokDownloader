package com.kalugin19.tiktokdownloader.repository

import androidx.lifecycle.LiveData
import com.kalugin19.tiktokdownloader.data.ApiResult
import com.kalugin19.tiktokdownloader.data.Video

interface TikTokRepository {

    val downloadingRequestLiveData: LiveData<ApiResult<Video>?>

    suspend fun download(url: String)

}