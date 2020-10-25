package com.kalugin19.tiktokdownloader.data

import java.io.File

data class Video(
    val title: String,
    val url: String,
    val file: File
)