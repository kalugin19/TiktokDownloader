package com.kalugin19.tiktokdownloader.ui.main

import com.kalugin19.tiktokdownloader.util.*
import org.json.JSONObject
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

object TikTokUrlParser {

    private const val CSS_QUERY = "link[rel=\"canonical\"]"
    private const val ATTRIBUTE_KEY = "href"
    private const val VIDEO = "video/"

    fun parse(url: String): TikTokVideoUrl {
        val doc = Jsoup.connect(url).get()
        return doc.toDownloadLink()
    }

    private fun Document.toDownloadLink(): TikTokVideoUrl {
        var url = select(CSS_QUERY).last().attr(ATTRIBUTE_KEY)

        if (url.isNotEmpty() && url.contains(VIDEO)) {
            url = url
                .split(VIDEO)
                .toTypedArray()[1]
                .peelHtmlTags()

            return TikTokVideoUrl(
                title = title(),
                url = url
            )
        }
        throw RuntimeException("Link doesn't contain tiktok video")
    }

    private fun String.peelHtmlTags(): String {
        val doc = Jsoup
            .connect(TIK_TOK_API)
            .data(ID, this)
            .ignoreContentType(true)
            .headers(tikTokDownloadHeader)
            .get()

        return doc
            .body()
            .toString()
            .removeBodyTags()
            .run {
                JSONObject(this)
            }
            .getJSONObject(DETAIL)
            .getJSONObject(VIDEO_JSON_KEY)
            .getJSONObject(PLAY_ADDRESS)
            .getJSONArray(URLS_LIST)
            .getString(0)
    }

}