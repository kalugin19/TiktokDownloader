package com.kalugin19.tiktokdownloader.api

import com.kalugin19.tiktokdownloader.data.Video

interface TikTokApi {
    suspend fun download(url: String, updateProgress: (Int) -> Unit) : Video
}