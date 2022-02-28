package com.example.smarthome.data.api

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.smarthome.data.model.BasicResponse
import com.example.smarthome.data.model.Relay
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.*
import okhttp3.ResponseBody
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RelayService @Inject constructor(
    private val api: RelayEndpoints
) : DeviceService<Relay>
{
    private val _relays = MutableLiveData<List<Relay>>(emptyList())
            val relays: LiveData<List<Relay>> get() = _relays

    override suspend fun add(device: Relay): Response<BasicResponse<Relay>> {
        return api.add(device)
    }

    override suspend fun fetchDevices(): LiveData<List<Relay>> {
        _relays.value = api.getAll().body()
        return relays
    }

    override suspend fun deleteById(id: Long): Response<ResponseBody> {
        val response = api.deleteById(id)
        fetchDevices()
        return response
    }

    override suspend fun getDeviceById(id: Long): Relay {
        return api.getById(id)
    }

    override suspend fun updateDevice(id: Long, device: Relay): Response<Relay> {
        return api.updateById(id, device)
    }

    override suspend fun turn(id: Long): Response<Relay> {
        TODO("Not implemented")
    }

    suspend fun turnRelay(id: Long): Response<ResponseBody> {
        return api.turn(id)
    }
}
