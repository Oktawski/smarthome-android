package com.example.smarthome.data.api

import androidx.lifecycle.LiveData
import com.example.smarthome.data.model.WifiDevice

interface DeviceService<T : WifiDevice> {

    suspend fun add(device: T)
    fun fetchDevices(): LiveData<List<T>>
    fun deleteById(id: Long)
    //fun getDeviceById(id: Long): Single<T>
    suspend fun getDeviceById(id: Long): T
    fun updateDevice(id: Long, t: T)
    fun turn(id: Long)
}