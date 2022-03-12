package com.example.smarthome.ui.relay

import android.os.Bundle
import androidx.activity.viewModels
import com.example.smarthome.data.model.WifiDevice
import com.example.smarthome.databinding.DetailsRelayActivityBinding
import com.example.smarthome.ui.DeviceDetailsActivity
import com.example.smarthome.utilities.Resource
import com.example.smarthome.utilities.ViewHelper
import com.example.smarthome.viewmodel.RelayDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsRelayActivity :
    DeviceDetailsActivity(),
    ViewHelper
{
    override val viewModel: RelayDetailsViewModel by viewModels()
    private lateinit var binding: DetailsRelayActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DetailsRelayActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getDevice()

        initLiveDataObservers()
        initOnClickListeners()
    }

    override fun initLiveDataObservers() {
        viewModel.relay.observe(this) {
            inflateViews(it)
        }

        viewModel.status.observe(this){
            when (it.status) {
                Resource.Status.LOADING -> setLoadingLayout()
                Resource.Status.ADDED -> {
                    setNormalLayout()
                    toast(this, it.message)
                    finish()
                }
                else -> toast(this, it.message)
            }
        }
    }

    override fun initOnClickListeners(){
        binding.confirmButton.setOnClickListener {
            viewModel.setRelayProperties(binding.name.text.toString())
        }

        binding.cancelButton.setOnClickListener { finish() }
    }

    override fun getDevice() {
        viewModel.getById(intent.getLongExtra("relayId", -1))
    }

    override fun inflateViews(device: WifiDevice) {
        with (binding) {
            name.setText(device.name)
            mac.setText(device.mac)
        }
    }

    private fun setLoadingLayout() {
        with (binding) {
            hideViews(confirmButton, cancelButton)
        }
    }

    private fun setNormalLayout() {
        with (binding) {
            showViews(confirmButton, cancelButton)
        }
    }
}
