package com.kalugin19.tiktokdownloader.ui.videoplayer

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.kalugin19.tiktokdownloader.util.SingleLiveEvent
import java.io.File

class VideoPlayerViewModel : ViewModel() {

    private val _videoFileLiveData = SingleLiveEvent<File>()
    val videoFileLiveData: LiveData<File> = _videoFileLiveData

    fun showVideo(file: File?){
        file?.let {
            _videoFileLiveData.value = it
        }
    }
}