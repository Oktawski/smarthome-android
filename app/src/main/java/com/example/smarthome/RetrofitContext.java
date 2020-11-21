package com.example.smarthome;

import com.example.smarthome.ligths.services.ILightRetrofitService;
import com.example.smarthome.relays.services.IRelayRetrofitService;
import com.example.smarthome.user.models.JwtToken;
import com.example.smarthome.user.services.IUserRetrofitService;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitContext {
    private final static String BASE_URL = "http://192.168.1.107:8015";
    private static ILightRetrofitService lightService;
    private static IRelayRetrofitService relayService;
    private static IUserRetrofitService userService;


    private static Retrofit getRetrofitContext(){
        OkHttpClient.Builder okhttp = new OkHttpClient.Builder();
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();

        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        okhttp.addInterceptor(loggingInterceptor)
                .readTimeout(15, TimeUnit.SECONDS)
                .connectTimeout(15, TimeUnit.SECONDS);

        okhttp.addInterceptor(chain -> {
            Request request = chain.request()
                    .newBuilder()
                    .addHeader("Authorization", JwtToken.Companion.getJwtToken())
                    .build();

                    return chain.proceed(request);
        });

        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okhttp.build())
                .build();
    }

    public static IRelayRetrofitService getRelayService(){
        if(relayService == null){
            relayService = getRetrofitContext().create(IRelayRetrofitService.class);
        }
        return relayService;
    }

    public static IUserRetrofitService getUserService(){
        if(userService == null){
            userService = getRetrofitContext().create(IUserRetrofitService.class);
        }
        return userService;
    }

    public static ILightRetrofitService getLightService(){
        if(lightService == null){
            lightService = getRetrofitContext().create(ILightRetrofitService.class);
        }
        return lightService;
    }
}
