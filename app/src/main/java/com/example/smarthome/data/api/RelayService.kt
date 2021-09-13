package com.example.smarthome.data.api

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.smarthome.data.model.BasicResponse
import com.example.smarthome.data.model.Relay
import com.example.smarthome.utilities.Resource
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
) {
    
    private val TAG = "RelayService"

    private val _relays = MutableLiveData<List<Relay>>(emptyList())
            val relays: LiveData<List<Relay>> get() = _relays
    private val _status = MutableLiveData<Resource<Relay>>()
            val status: LiveData<Resource<Relay>> get() = _status

    var job: Job? = null
    private val disposable = CompositeDisposable()
    private val errorMessage = "Could not connect to server"

    fun add(relay: Relay){
        _status.value = Resource.loading()

        job = CoroutineScope(Dispatchers.IO).launch {
            val response = api.add(relay)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    _status.value = Resource.success(response.body()?.msg)
                    Log.i(TAG, "add: success")
                    fetchRelays()
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

    fun fetchRelays(): LiveData<List<Relay>> {
        disposable.add(api.getAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    val relays = it.body()
                    //if(relays != null) _relays.value = relays
                    _relays.value = relays
                    _status.value = Resource.success()
                    _status.value = Resource.none()
                    Log.i(TAG, "fetchRelays: success")
                },
                { _status.value = Resource.error(errorMessage) }
            ))
        return this.relays
    }

    fun deleteById(id: Long) {
        disposable.add(api.deleteById(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    if(it.isSuccessful) fetchRelays()
                },
                { _status.value = Resource.error(errorMessage) }
            ))
    }

    fun getRelayById(id: Long): Single<Relay> {
        return api.getById(id)
    }

    fun updateRelay(id: Long, relay: Relay){
        _status.value = Resource.loading()

        disposable.add(api.updateById(id, relay)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    if(it.isSuccessful) _status.value = Resource.success("Updated")
                    fetchRelays()
                    _status.value = Resource.none()
                },
                { _status.value = Resource.error(errorMessage) }
            ))
    }

    fun turn(id: Long){
        disposable.add(api.turn(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { if(it.isSuccessful) fetchRelays() },
                { _status.value = Resource.error(errorMessage) }))
    }

}
