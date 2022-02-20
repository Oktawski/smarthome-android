package com.example.smarthome.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smarthome.data.api.RelayService
import com.example.smarthome.data.model.Relay
import com.example.smarthome.utilities.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RelayViewModel @Inject constructor(
    private val service: RelayService
) : ViewModel()
{
    val relays: LiveData<List<Relay>> = service.relays
    val status: LiveData<Resource<Relay>> = service.status

    fun add(t: Relay) {
        viewModelScope.launch { service.add(t) }
    }

    fun fetch(): LiveData<List<Relay>> {
        viewModelScope.launch {
            service.fetchDevices()
        }
        TODO("Not implemented")
    }

    fun deleteById(id: Long) {
        viewModelScope.launch {
            service.deleteById(id)
        }
    }

    suspend fun getById(id: Long): Relay {
        return service.getDeviceById(id)
    }
    fun turn(id: Long) {
        viewModelScope.launch {
            service.turn(id)
        }
        TODO("Not implemented")
    }
    fun updateDevice(id: Long, t: Relay) {
        viewModelScope.launch {
            service.updateDevice(id, t)
        }
    }

}
