package com.kalugin19.tiktokdownloader.ui.videoplayer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kalugin19.tiktokdownloader.databinding.VideoFragmentBinding


class VideoPlayerFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return VideoFragmentBinding.inflate(inflater, container, false)
                .apply {
                    url = requireArguments().getUrl()
                    lifecycle = this@VideoPlayerFragment
                    lifecycleOwner = this@VideoPlayerFragment
                }
                .root
    }

    companion object {
        private const val URL_KEY = "url"
        fun newInstance(url: String) = VideoPlayerFragment().apply {
            this.arguments = Bundle().apply { putUrl(url) }
        }

        private fun Bundle.getUrl() = getString(URL_KEY)

        private fun Bundle.putUrl(url: String) = putString(URL_KEY, url)
    }
}