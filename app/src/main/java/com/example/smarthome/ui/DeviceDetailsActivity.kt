package com.example.smarthome.ui

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import com.example.smarthome.data.model.WifiDevice
import com.example.smarthome.utilities.LiveDataObservers
import com.example.smarthome.utilities.OnClickListeners

abstract class DeviceDetailsActivity
    : AppCompatActivity(),
    OnClickListeners,
    LiveDataObservers
{
    abstract val viewModel: ViewModel
    //var deviceId: Long? = null

    abstract fun getDevice()
    abstract fun inflateViews(device: WifiDevice)
}
