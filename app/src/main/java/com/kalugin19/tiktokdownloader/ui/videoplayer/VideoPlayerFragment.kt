package com.kalugin19.tiktokdownloader.ui.videoplayer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.kalugin19.tiktokdownloader.databinding.VideoFragmentBinding
import java.io.File


class VideoPlayerFragment : Fragment() {

    companion object {
        fun newInstance() = VideoPlayerFragment()
    }

    private val videoViewModel: VideoPlayerViewModel by lazy {
        requireActivity()
            .application
            .let {
                defaultViewModelProviderFactory
            }
            .let { factory ->
                ViewModelProvider(this, factory)
            }
            .get(VideoPlayerViewModel::class.java)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return VideoFragmentBinding.inflate(inflater, container, false)
                .apply {
                    lifecycle = this@VideoPlayerFragment
                    viewModel = videoViewModel
                    lifecycleOwner = this@VideoPlayerFragment
                }
                .root
    }

    fun showVideo(file: File){
        videoViewModel.showVideo(file)
    }

}
