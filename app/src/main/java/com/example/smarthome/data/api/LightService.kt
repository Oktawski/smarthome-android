package com.example.smarthome.data.api

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.smarthome.data.model.BasicResponse
import com.example.smarthome.data.model.Light
import okhttp3.ResponseBody
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LightService @Inject constructor(
    private val api: LightEndpoints
) : DeviceService<Light>
{
    val lights = MutableLiveData<List<Light>>(emptyList())

    override suspend fun add(device: Light): Response<BasicResponse<Light>> {
        return api.add(device)
    }

    override suspend fun fetchDevices(): LiveData<List<Light>> {
        lights.value = api.getAll().body()
        return lights
    }

    override suspend fun deleteById(id: Long): Response<ResponseBody> {
        val response = api.deleteById(id)
        fetchDevices()
        return response
    }

    override suspend fun getDeviceById(id: Long): Light {
        return api.getById(id)
    }

    override suspend fun updateDevice(id: Long, device: Light): Response<Light> {
        return api.updateById(id, device)
    }

    override suspend fun turn(id: Long): Response<Light> {
        return api.turn(id)
    }

    suspend fun setColor(id: Long, light: Light): Response<ResponseBody> {
        return api.setColor(id, light)
    }
}


