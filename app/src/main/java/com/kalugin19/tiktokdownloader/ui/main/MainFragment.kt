package com.kalugin19.tiktokdownloader.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
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
        if (savedInstanceState == null) {
            val videoFragment = VideoPlayerFragment.newInstance()
            childFragmentManager {
                replace(R.id.video_fragment_container, videoFragment, TAG)
                        .hide(videoFragment)
            }
        }

        viewModel.errorMediatorLiveData.observe(viewLifecycleOwner, {
            if (it.isNotEmpty()) {
                Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
            }
        })

        viewModel.addressLiveData.observe(viewLifecycleOwner, {
            childFragmentManager.findVideoFragment()?.let { videoFragment ->
                if (it.isNullOrEmpty()) {
                    childFragmentManager {
                        hide(videoFragment)
                    }
                }
            }
        })

        viewModel.videoUrlLiveData.observe(viewLifecycleOwner, { video ->
            video?.apply {
                val videoFragment = childFragmentManager.findVideoFragment()
                if (videoFragment != null) {
                    childFragmentManager {
                        show(videoFragment)
                    }
                    videoFragment.showVideo(video)
                }
            }
        })

    }

    private fun FragmentManager.findVideoFragment(): VideoPlayerFragment? {
        return findFragmentByTag(TAG) as VideoPlayerFragment?
    }
}

operator fun FragmentManager.invoke(func: FragmentTransaction.() -> FragmentTransaction) {
    beginTransaction()
            .func()
            .commit()
}
