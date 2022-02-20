package com.example.smarthome.data.api

import androidx.lifecycle.LiveData
import com.example.smarthome.data.model.BasicResponse
import com.example.smarthome.data.model.WifiDevice
import okhttp3.ResponseBody
import retrofit2.Response

interface DeviceService<T : WifiDevice> {

    suspend fun add(device: T): Response<BasicResponse<T>>
    suspend fun fetchDevices(): LiveData<List<T>>
    suspend fun deleteById(id: Long): Response<ResponseBody>
    suspend fun getDeviceById(id: Long): T
    suspend fun updateDevice(id: Long, device: T): Response<T>
    suspend fun turn(id: Long): Response<T>
}