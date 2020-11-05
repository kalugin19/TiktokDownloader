package com.kalugin19.tiktokdownloader.api

import com.kalugin19.tiktokdownloader.data.Video
import com.kalugin19.tiktokdownloader.util.LINK_KEY
import com.kalugin19.tiktokdownloader.util.TIK_TOK_API
import org.jsoup.Jsoup
import java.io.File

class TikTokApi2(
        private val loadTikTokVideo: (
                redirectedUrl: String,
                onResult: (String) -> Unit
        ) -> Unit
) : TikTokApi {

    override suspend fun download(url: String, onResult: (Video) -> Unit, onUpdate: (Int) -> Unit) {
        val redirectedUrl = url.toRedirectedUrl()

        loadTikTokVideo(redirectedUrl) {
            val video = Video("test", it, File("testFile"))
            onResult(video)
        }
    }
}

private fun String.toRedirectedUrl(): String {
    return "$TIK_TOK_API?$LINK_KEY=$this"
}

