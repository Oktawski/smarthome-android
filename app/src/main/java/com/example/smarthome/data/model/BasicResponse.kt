package com.example.smarthome.data.model

import com.google.gson.annotations.SerializedName

data class BasicResponse<T: WifiDevice>(
    @SerializedName("object") val t: T,
    @SerializedName("msg") val msg: String)

