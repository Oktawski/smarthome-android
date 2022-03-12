package com.example.smarthome.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smarthome.data.api.LightService
import com.example.smarthome.data.model.BasicResponse
import com.example.smarthome.data.model.Light
import com.example.smarthome.utilities.Resource
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LightViewModel @Inject constructor(
    private val service: LightService
) : ViewModel()
{
    val lights = service.lights

    private val _status = MutableLiveData<Resource<Light>>()
            val status: LiveData<Resource<Light>> get() = _status

    fun add(t: Light) {
        viewModelScope.launch {
            val response = service.add(t)
            if (response.isSuccessful) {
                _status.value = Resource.added(response.body()?.t, response.body()?.msg)
                fetchDevices()
            } else {
                val gson = Gson()
                val type = object : TypeToken<BasicResponse<Light>>() {}.type
                val errorResponse: BasicResponse<Light> =
                    gson.fromJson(response.errorBody()?.charStream(), type)
                _status.value = Resource.error(errorResponse.msg, null)
            }
        }
    }

    fun fetchDevices(): LiveData<List<Light>> {
        _status.value = Resource.loading()
        viewModelScope.launch {
            service.fetchDevices()
            _status.value = Resource.success()
        }
        return lights
    }

    fun deleteById(id: Long) {
        _status.value = Resource.loading()
        viewModelScope.launch {
            val response = service.deleteById(id)
            _status.value = if (response.isSuccessful)
                                Resource.success("Device removed")
                            else
                                Resource.error("Device could not be removed")
            fetchDevices()
        }
    }
}