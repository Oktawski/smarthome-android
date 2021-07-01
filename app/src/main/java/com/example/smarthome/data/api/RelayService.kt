package com.example.smarthome.data.api

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.smarthome.BasicResponse
import com.example.smarthome.RetrofitContext
import com.example.smarthome.data.model.Relay
import com.example.smarthome.utilities.Resource
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class RelayService {
    private val api: RelayEndpoints = RetrofitContext().getInstance(RelayEndpoints::class.java)
    private val _relays = MutableLiveData<List<Relay>>(emptyList())
    val relays: LiveData<List<Relay>> get() = _relays
    private val _status = MutableLiveData<Resource<Relay>>()
    val status: LiveData<Resource<Relay>> get() = _status


    companion object {
        private var instance: RelayService? = null

        @JvmStatic
        fun getInstance(): RelayService? {
            if (instance == null) {
                instance = RelayService()
            }
            return instance
        }
    }

    private val disposable = CompositeDisposable()
    private val errorMessage = "Could not connect to server"

    fun add(relay: Relay){
        _status.value = Resource.loading()

        disposable.add(api.add(relay)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    if(it.isSuccessful){
                        _status.value = Resource.success(it.body()?.msg)
                        fetchRelays()
                    }else {
                        val gson = Gson()
                        val type = object: TypeToken<BasicResponse<Relay>>() {}.type
                        val errorResponse: BasicResponse<Relay> =
                            gson.fromJson(it.errorBody()?.charStream(), type)

                        _status.value = Resource.error(errorResponse.msg, null)
                    }
                    _status.value = Resource.none()
                },
                {
                    _status.value = Resource.error(errorMessage)
                }
            ))
    }

    fun fetchRelays(): LiveData<List<Relay>> {
        disposable.add(api.getAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    val relays = it.body()
                    if(!relays.isNullOrEmpty()) this._relays.value = relays
                    _status.value = Resource.success()
                    _status.value = Resource.none()
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

    fun getRelayById(id: Long): LiveData<Relay> {
        Log.i("TAG", "getRelayById: $id")
        throw NotImplementedError()
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