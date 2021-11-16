package com.example.smarthome.ui.light

import android.os.Bundle
import androidx.activity.viewModels
import com.example.smarthome.data.model.Light
import com.example.smarthome.data.model.WifiDevice
import com.example.smarthome.databinding.DetailsLightActivityBinding
import com.example.smarthome.ui.DeviceDetailsActivity
import com.example.smarthome.utilities.LiveDataObservers
import com.example.smarthome.utilities.OnClickListeners
import com.example.smarthome.viewmodel.LightViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsLightActivity
    : DeviceDetailsActivity(),
    OnClickListeners,
    LiveDataObservers
{
    override val viewModel: LightViewModel by viewModels()
    private lateinit var device: Light
    private lateinit var binding: DetailsLightActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DetailsLightActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        deviceId = intent.getLongExtra("lightId", -1)
        getDevice()

        initLiveDataObservers()
        initOnClickListeners()
    }

    override fun initLiveDataObservers() {
        TODO("Not yet implemented")
    }

    override fun initOnClickListeners() {
        TODO("Not yet implemented")
    }

    override fun getDevice() {
        TODO("akjsf")
    }

    override fun inflateViews(device: WifiDevice) {
        TODO("Not yet implemented")
    }
}
