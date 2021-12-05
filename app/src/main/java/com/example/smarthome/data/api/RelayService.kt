package com.example.smarthome.data.api

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.smarthome.data.model.BasicResponse
import com.example.smarthome.data.model.Relay
import com.example.smarthome.utilities.Resource
import com.example.smarthome.data.api.DeviceService
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RelayService @Inject constructor(
    private val api: RelayEndpoints
) : DeviceService<Relay>
{
    private val TAG = "RelayService"

    private val _relays = MutableLiveData<List<Relay>>(emptyList())
            val relays: LiveData<List<Relay>> get() = _relays
    private val _status = MutableLiveData<Resource<Relay>>()
            val status: LiveData<Resource<Relay>> get() = _status

    var job: Job? = null
    private val disposable = CompositeDisposable()
    private val errorMessage = "Could not connect to server"

    override suspend fun add(device: Relay) {
        _status.value = Resource.loading()

        job = CoroutineScope(Dispatchers.IO).launch {
            val response = api.add(device)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    _status.value = Resource.added(response.body()?.t, response.body()?.msg)
                    Log.i(TAG, "add: success")
                    fetchDevices()
                }
                else {
                    val gson = Gson()
                    val type = object : TypeToken<BasicResponse<Relay>>() {}.type
                    val errorResponse: BasicResponse<Relay> =
                        gson.fromJson(response.errorBody()?.charStream(), type)
                    _status.value = Resource.error(errorResponse.msg, null)
                }
            }
        }
    }

    override fun fetchDevices(): LiveData<List<Relay>> {
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = api.getAll()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val mRelays = response.body()
                    _relays.value = mRelays
                    _status.value = Resource.success()
                    _status.value = Resource.none()
                } else {
                    _status.value = Resource.error(errorMessage)
                }
            }
        }
        return relays
    }

    override fun deleteById(id: Long) {
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = api.deleteById(id)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    fetchDevices()
                } else {
                    _status.value = Resource.error(errorMessage)
                }
            }
        }
    }

    override suspend fun getDeviceById(id: Long): Relay {
        return api.getById(id)
    }

    override fun updateDevice(id: Long, device: Relay) {
        _status.value = Resource.loading()

        job = CoroutineScope(Dispatchers.IO).launch {
            val response = api.updateById(id, device)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    _status.value = Resource.added(response.body())
                    fetchDevices()
                    _status.value = Resource.none()
                } else {
                    _status.value = Resource.error(errorMessage)
                }


            }
        }

/*        disposable.add(api.updateById(id, device)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    if(it.isSuccessful) _status.value = Resource.success("Updated")
                    fetchDevices()
                    _status.value = Resource.none()
                },
                { _status.value = Resource.error(errorMessage) }
            ))*/
    }

    override fun turn(id: Long) {
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = api.turn(id)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    fetchDevices()
                } else {
                    _status.value = Resource.error(errorMessage)
                }
            }
        }
    }
}
