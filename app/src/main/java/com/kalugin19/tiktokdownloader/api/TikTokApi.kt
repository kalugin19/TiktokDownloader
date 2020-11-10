package com.kalugin19.tiktokdownloader.api

import com.kalugin19.tiktokdownloader.data.Video

interface TikTokApi {

//    https://tikmate.online/tiktok/6877472525761154306
    suspend fun download(url: String, onResult: (Video) -> Unit, onUpdate: (Int) -> Unit)
}