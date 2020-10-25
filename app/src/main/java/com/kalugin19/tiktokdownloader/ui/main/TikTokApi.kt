package com.kalugin19.tiktokdownloader.ui.main


interface TikTokApi {
    suspend fun getDownloadingUrl(url: String) :  Pair<String, String>
}