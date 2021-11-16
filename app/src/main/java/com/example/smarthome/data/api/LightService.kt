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
) {

    private val _lights = MutableLiveData<List<Light>>(emptyList())
            val lights: LiveData<List<Light>> get() = _lights
    private val _status = MutableLiveData<Resource<Light>>()
            val status: LiveData<Resource<Light>> get() = _status

    var job: Job? = null

    fun add(light: Light) {
        _status.value = Resource.loading()

        job = CoroutineScope(Dispatchers.IO).launch {
            val response = api.add(light)
            withContext(Dispatchers.Main) {
                if ( response.isSuccessful) {
                    _status.value = Resource.success(response.body()?.msg)
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

    fun deleteById(id: Long) {}

    fun turn(id: Long) {}

    fun fetchDevices() {

    }



}


