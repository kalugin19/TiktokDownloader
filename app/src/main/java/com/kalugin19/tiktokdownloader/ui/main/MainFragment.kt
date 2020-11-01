package com.kalugin19.tiktokdownloader.ui.main

import android.annotation.SuppressLint
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
        fun newInstance() = MainFragment()
    }

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(
                this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        ).get(
                MainViewModel::class.java
        )
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

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.videoUrlLiveData.observe(viewLifecycleOwner) { url ->
            url?.apply {
                childFragmentManager
                    .beginTransaction()
                    .replace(
                        R.id.video_fragment_container,
                        VideoPlayerFragment.newInstance(this),
                        "VideoFragment"
                    )
                    .commit()
            }
        }
    }
}
