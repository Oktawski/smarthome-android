package com.example.smarthome.viewmodel

import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smarthome.data.api.LightService
import com.example.smarthome.data.model.Light
import com.example.smarthome.utilities.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LightDetailsViewModel @Inject constructor(
    private val service: LightService
) : ViewModel()
{
    private val _status = MutableLiveData<Resource<Light>>()
            val status: LiveData<Resource<Light>> get() = _status

    private val _light: MutableLiveData<Light> = MutableLiveData()
            val light: LiveData<Light> get() = _light

    fun getById(id: Long) {
        viewModelScope.launch {
            _light.value = service.getDeviceById(id)
        }
    }

    fun updateDevice() {
        _status.value = Resource.loading()
        viewModelScope.launch {
            val response = service.updateDevice(light.value?.id!!, light.value!!)
            _status.value = if (response.isSuccessful)
                Resource.added(light.value!!, "Light updated")
            else
                Resource.error("Something went wrong")
        }
    }

    fun setColor(rgb: Int) {
        _light.value?.apply {
            red = rgb.red
            green = rgb.green
            blue = rgb.blue
        }
        updateRequest()
    }

    fun setIntensity(intensity: Int) {
        _light.value?.intensity = intensity
        updateRequest()
    }

    fun setName(name: String) {
        _light.value?.name = name
        updateRequest()
    }

    private fun updateRequest() {
        viewModelScope.launch {
            service.setColor(light.value?.id!!, light.value!!)
        }
    }
}