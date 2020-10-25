package com.kalugin19.tiktokdownloader.api


class TikTokParserImpl : TikTokParser {

    override suspend fun getDownloadingUrl(url: String): Pair<String, String> {
        return TikTokUrlParser.parse(url)
            .run {
                title to this.url
            }
    }

}