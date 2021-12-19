package com.example.smarthome.viewmodel

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
class LightViewModel @Inject constructor(
    private val service: LightService
) : ViewModel()
{
    val lights: LiveData<List<Light>> = service.lights
    val status: LiveData<Resource<Light>> = service.status

    val light: MutableLiveData<Light> = MutableLiveData()


    fun add(t: Light) {
        viewModelScope.launch { service.add(t) }
    }

    fun fetch(): LiveData<List<Light>> = service.fetchDevices()

    fun deleteById(id: Long) {
        viewModelScope.launch {
            service.deleteById(id)
        }
    }

    suspend fun getById(id: Long): Light {
        light.value = service.getDeviceById(id)
        return light.value!!
    }

    fun turn(id: Long) = service.turn(id)
    fun setColor(id: Long, light: Light) = service.setColor(id, light)
    fun updateDevice(id: Long, device: Light) = service.updateDevice(id, device)

}