package com.kalugin19.tiktokdownloader.api

import com.kalugin19.tiktokdownloader.data.Video
import com.kalugin19.tiktokdownloader.util.MP4_EXT
import java.io.DataOutputStream
import java.io.EOFException
import java.io.File
import java.io.FileOutputStream
import java.net.URL

class TikTokApiImpl(
    private val dir: File,
    private val tikTokParser: TikTokParser
) : TikTokApi {

    override suspend fun download(url: String, updateProgress: (Int) -> Unit): Video {
        val titleLinkPair = tikTokParser.getDownloadingUrl(url)
        val title = titleLinkPair.first
        val link = titleLinkPair.second
        val file = link.downloadFile(title, updateProgress)
        return Video(title, link, file)
    }

    private fun String.downloadFile(fileName: String, updateProgress: (Int) -> Unit): File {
        val file = File(dir, "${fileName}$MP4_EXT")

        val u = URL(this)
        val conn = u.openConnection()

        val contentLength: Int = conn.contentLength
        val inputStream = u.openStream()
//        val stream = DataInputStream(inputStream)
        val buffer = ByteArray(contentLength)

        var n = 0
        val length = buffer.size


        while (n < buffer.size) {
            val count = inputStream.read(buffer, n, length - n)
            if (count < 0) {
                throw EOFException()
            }
            n += count
            val progress = (n * 100) / contentLength
            updateProgress(progress)
//            publishProgress("" + (int) ((total * 100) / lenghtOfFile));
        }
//        stream.readFully(buffer)
//        stream.close()
        inputStream.close()
        val fos = DataOutputStream(FileOutputStream(file))

        try {
            fos.write(buffer)
        } finally {
            fos.flush()
            fos.close()
        }

        return file
    }
}