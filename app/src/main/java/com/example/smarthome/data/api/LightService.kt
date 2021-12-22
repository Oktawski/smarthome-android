package com.example.smarthome.data.api

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.smarthome.data.model.BasicResponse
import com.example.smarthome.data.model.Light
import com.example.smarthome.utilities.Resource
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LightService @Inject constructor(
    private val api: LightEndpoints
) : DeviceService<Light>
{
    private val _lights = MutableLiveData<List<Light>>(emptyList())
            val lights: LiveData<List<Light>> get() = _lights
    private val _status = MutableLiveData<Resource<Light>>()
            val status: LiveData<Resource<Light>> get() = _status

    var job: Job? = null

    override suspend fun add(light: Light) {
        _status.value = Resource.loading()

        job = CoroutineScope(Dispatchers.IO).launch {
            val response = api.add(light)
            withContext(Dispatchers.Main) {
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
    }

    override fun fetchDevices(): LiveData<List<Light>> {
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = api.getAll()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val mLights = response.body()
                    _lights.value = mLights
                    _status.value = Resource.success()
                    _status.value = Resource.none()
                } else {
                    _status.value = Resource.error("Error")
                }
            }
        }
        return lights
    }

    override fun deleteById(id: Long) {
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = api.deleteById(id)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    fetchDevices()
                } else {
                    _status.value = Resource.error("error")
                }
                fetchDevices()
            }
        }
    }

    override suspend fun getDeviceById(id: Long): Light {
        return api.getById(id)
    }

    override fun updateDevice(id: Long, device: Light) {
        _status.value = Resource.loading()

        job = CoroutineScope(Dispatchers.IO).launch {
            val response = api.updateById(id, device)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    _status.value = Resource.added(response.body())
                    fetchDevices()
                    _status.value = Resource.none()
                } else {
                    _status.value = Resource.error("Error")
                }
            }
        }
    }

    override fun turn(id: Long) {
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = api.turn(id)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    fetchDevices()
                } else {
                    _status.value = Resource.error("Error")
                }
            }
        }
    }

    fun setColor(id: Long, light: Light) {
        _status.value = Resource.loading()
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = api.setColor(id, light)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    _status.value = Resource.success()
                }
            }
        }
    }





}


