package com.example.smarthome.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.smarthome.data.api.RelayService
import com.example.smarthome.data.model.Relay
import com.example.smarthome.utilities.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RelayViewModel @Inject constructor(
    private val service: RelayService
) : ViewModel(),
    ServiceCalls<Relay>
{
    val relays: LiveData<List<Relay>> = service.relays
    val status: LiveData<Resource<Relay>> = service.status

    override fun add(t: Relay) = service.add(t)
    override fun fetch(): LiveData<List<Relay>> = service.fetchRelays()
    override fun deleteById(id: Long) = service.deleteById(id)
    override fun getById(id: Long) = service.getRelayById(id)
    override fun turn(id: Long) = service.turn(id)
    override fun update(id: Long, t: Relay) = service.updateRelay(id, t)

}
