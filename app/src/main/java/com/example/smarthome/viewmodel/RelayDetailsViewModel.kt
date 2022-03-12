package com.example.smarthome.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smarthome.data.api.RelayService
import com.example.smarthome.data.model.Light
import com.example.smarthome.data.model.Relay
import com.example.smarthome.utilities.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RelayDetailsViewModel @Inject constructor(
    private val service: RelayService
) : ViewModel()
{
    private val _status = MutableLiveData<Resource<Relay>>()
            val status: LiveData<Resource<Relay>> get() = _status

    private val _relay: MutableLiveData<Relay> = MutableLiveData()
            val relay: LiveData<Relay> get() = _relay

    fun getById(id: Long) {
        viewModelScope.launch {
            _relay.value = service.getDeviceById(id)
        }
    }

    fun setRelayProperties(name: String) {
        _relay.value?.name = name
        updateDevice()
    }

    private fun updateDevice() {
        _status.value = Resource.loading()
        viewModelScope.launch {
            val response = service.updateDevice(relay.value?.id!!, relay.value!!)
            _status.value = if (response.isSuccessful)
                Resource.added(relay.value!!, "Relay updated")
            else
                Resource.error("Something went wrong")
        }
    }
}