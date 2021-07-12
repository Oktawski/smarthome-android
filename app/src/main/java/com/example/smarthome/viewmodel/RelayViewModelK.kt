package com.example.smarthome.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.smarthome.data.api.RelayService
import com.example.smarthome.data.model.Relay
import com.example.smarthome.utilities.Resource

class RelayViewModelK : ViewModel() {

    private val service: RelayService = RelayService.getInstance()

    val relays: LiveData<List<Relay>> = service.relays
    val status: LiveData<Resource<Relay>> = service.status

    fun add(relay: Relay) = service.add(relay)
    fun delete(id: Long) = service.deleteById(id)
    fun fetch() = service.fetchRelays()
    fun getRelayById(id: Long) = service.getRelayById(id)
    fun turn(id: Long?) = service.turn(id!!)
    fun update(id: Long, relay: Relay) = service.updateRelay(id, relay)
}