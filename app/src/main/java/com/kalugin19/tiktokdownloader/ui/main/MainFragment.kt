package com.kalugin19.tiktokdownloader.ui.main

import android.Manifest
import android.app.DownloadManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputLayout
import com.kalugin19.tiktokdownloader.R
import com.kalugin19.tiktokdownloader.TikTokDownloaderApplication
import com.kalugin19.tiktokdownloader.databinding.MainFragmentBinding

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(
                this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(activity!!.application)
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

}