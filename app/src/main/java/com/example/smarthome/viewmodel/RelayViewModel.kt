package com.example.smarthome.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smarthome.data.api.RelayService
import com.example.smarthome.data.model.BasicResponse
import com.example.smarthome.data.model.Light
import com.example.smarthome.data.model.Relay
import com.example.smarthome.utilities.Resource
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RelayViewModel @Inject constructor(
    private val service: RelayService
) : ViewModel()
{
    val relays: LiveData<List<Relay>> = service.relays

    private val _status = MutableLiveData<Resource<Relay>>()
            val status: LiveData<Resource<Relay>> get() = _status

    fun add(t: Relay) {
        viewModelScope.launch {
            val response = service.add(t)
            if (response.isSuccessful) {
                _status.value = Resource.success("Relay added")
                fetchDevices()
            } else {
                val gson = Gson()
                val type = object : TypeToken<BasicResponse<Relay>>() {}.type
                val errorResponse: BasicResponse<Relay> =
                    gson.fromJson(response.errorBody()?.charStream(), type)
                _status.value = Resource.error(errorResponse.msg, null)
            }
        }
    }

    fun fetchDevices(): LiveData<List<Relay>> {
        _status.value = Resource.loading()
        viewModelScope.launch {
            service.fetchDevices()
            _status.value = Resource.success()
        }
        return relays
    }

    fun deleteById(id: Long) {
        viewModelScope.launch {
            service.deleteById(id)
        }
    }
}
