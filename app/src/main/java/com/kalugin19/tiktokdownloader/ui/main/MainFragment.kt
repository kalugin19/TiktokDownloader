package com.kalugin19.tiktokdownloader.ui.main

import android.Manifest
import android.app.DownloadManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.PermissionRequest
import android.widget.Button
import android.widget.EditText
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputLayout
import com.kalugin19.tiktokdownloader.R

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val downloadManager: DownloadManager by lazy {
        activity!!.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
    }

    private lateinit var viewModel: MainViewModel

    private val launcher = this.registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
        if (it) {
            val urlEt = view!!.findViewById<EditText>(R.id.message)
            viewModel.download(urlEt.text.toString())
        } else {
            shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(activity!!.application)
        ).get(
            MainViewModel::class.java
        )

        viewModel.errorLiveData.observe(this, Observer {

        })
        viewModel.successLiveData.observe(this, Observer {
            val downloadSessionId = downloadManager.enqueue(it)
        })

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val downloadBtn = view.findViewById<Button>(R.id.download)
        val textInput = view.findViewById<TextInputLayout>(R.id.address_text_input)
        textInput.isEndIconVisible = true
        downloadBtn.setOnClickListener {
            when {
                ContextCompat.checkSelfPermission(
                    activity!!,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED -> {
                    val urlEt = view!!.findViewById<EditText>(R.id.message)
                    viewModel.download(urlEt.text.toString())
                }
                else -> {
                    // You can directly ask for the permission.
                    // The registered ActivityResultCallback gets the result of this request.
                    launcher?.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                }
            }
        }
    }

}