package com.kalugin19.tiktokdownloader.ui.main


class TikTokApiImpl : TikTokApi {

    override suspend fun getDownloadingUrl(url: String): Pair<String, String> {
        return TikTokUrlParser.parse(url)
            .run {
                title to this.url
            }
    }

}