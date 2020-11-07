package com.kalugin19.tiktokdownloader.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.kalugin19.tiktokdownloader.R
import com.kalugin19.tiktokdownloader.databinding.MainFragmentBinding
import com.kalugin19.tiktokdownloader.ui.videoplayer.VideoPlayerFragment

class MainFragment : Fragment() {

    companion object {
        private const val TAG = "VideoFragment"

        fun newInstance() = MainFragment()
    }

    private val viewModel: MainViewModel by lazy {
        requireActivity()
            .application
            .let {
                ViewModelProvider.AndroidViewModelFactory.getInstance(it)
            }
            .let { factory ->
                ViewModelProvider(this, factory)
            }
            .get(MainViewModel::class.java)
    }

    private val launcher = this.registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
        viewModel.handlePermissionsResult(it)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel.launcher = launcher
        return MainFragmentBinding.inflate(inflater, container, false)
            .run {
                viewModel = this@MainFragment.viewModel
                lifecycleOwner = this@MainFragment
                root
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState == null){
            childFragmentManager
                .beginTransaction()
                .replace(
                    R.id.video_fragment_container,
                    VideoPlayerFragment.newInstance(),
                    TAG
                )
                .commit()
        }

        viewModel.videoUrlLiveData.observe(viewLifecycleOwner, { video ->
            video?.apply {
                val videoFragment: VideoPlayerFragment = childFragmentManager.findFragmentByTag(TAG) as VideoPlayerFragment
                videoFragment.showVideo(this)
            }
        })

    }
}
