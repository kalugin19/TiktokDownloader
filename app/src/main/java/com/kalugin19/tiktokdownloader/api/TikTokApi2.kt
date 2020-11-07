package com.kalugin19.tiktokdownloader.api

import com.kalugin19.tiktokdownloader.data.Video
import com.kalugin19.tiktokdownloader.util.LINK_KEY
import com.kalugin19.tiktokdownloader.util.MP4_EXT
import com.kalugin19.tiktokdownloader.util.TIK_TOK_API
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.net.URL
import java.util.*

class TikTokApi2(
    private val videoDirectory: File,
    private val loadTikTokVideo: (
        redirectedUrl: String,
        onResult: (Result<String>) -> Unit
    ) -> Unit
) : TikTokApi {

    override suspend fun download(url: String, onResult: (Video) -> Unit, onUpdate: (Int) -> Unit) {
        val redirectedUrl = url.toRedirectedUrl()

        loadTikTokVideo(redirectedUrl) {
            if (it.isFailure) {
                throw it.exceptionOrNull()!!
            }

            GlobalScope.launch(Dispatchers.IO){
                val downloadUrl = it.getOrNull()!!

                val fileName = UUID.randomUUID().toString()

                val file = download(fileName, downloadUrl)

                val video = Video(fileName, downloadUrl, file)
                onResult(video)
            }
        }
    }

    private fun download(fileName: String, link: String): File {
        return File(videoDirectory, "$fileName.$MP4_EXT").apply {
            URL(link).openStream().use { input ->
                FileOutputStream(this).use { output ->
                    input.copyTo(output)
                }
            }
        }
    }
}


private fun String.toRedirectedUrl(): String {
    return "$TIK_TOK_API?$LINK_KEY=$this"
}

