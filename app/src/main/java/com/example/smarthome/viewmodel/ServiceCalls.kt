package com.example.smarthome.viewmodel

import androidx.lifecycle.LiveData
import com.example.smarthome.data.model.WifiDevice
import io.reactivex.Single

sealed interface ServiceCalls<T : WifiDevice> {

    fun add(t: T)
    fun fetch(): LiveData<List<T>>
    fun deleteById(id: Long)
    fun getById(id: Long): Single<T>
    fun update(id: Long, t: T)
    fun turn(id: Long)
}