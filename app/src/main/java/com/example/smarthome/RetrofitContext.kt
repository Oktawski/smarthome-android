package com.example.smarthome

import com.example.smarthome.data.model.JwtToken
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitContext {
    private val BASE_URL = "http://192.168.1.105:8015"

    fun getRetrofitContext(): Retrofit{
        val okHttp = OkHttpClient.Builder()
        val loggingInterceptor = HttpLoggingInterceptor()

        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        okHttp.addInterceptor(loggingInterceptor)
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)

        okHttp.addNetworkInterceptor {
            val request = it.request()
                .newBuilder()
                .addHeader("Authorization", JwtToken.jwtToken)
                .build()

            it.proceed(request)
        }

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttp.build())
            .build()
    }

    fun <T> getInstance(apiType: Class<T>): T {
        return getRetrofitContext().create(apiType)
    }


}