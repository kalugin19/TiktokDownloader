package com.kalugin19.tiktokdownloader.api


interface TikTokParser {
    suspend fun getDownloadingUrl(url: String) :  Pair<String, String>
}