package com.example.smarthome;

import com.example.smarthome.ligths.services.LightEndpoints;
import com.example.smarthome.relays.services.RelayEndpoints;
import com.example.smarthome.user.models.JwtToken;
import com.example.smarthome.user.UserEndpoints;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitContext {
    private final static String BASE_URL = "http://192.168.1.105:8015";
    private static LightEndpoints lightService;
    private static RelayEndpoints relayService;
    private static UserEndpoints userService;


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
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okhttp.build())
                .build();
    }

    public static RelayEndpoints getRelayService(){
        if(relayService == null){
            relayService = getRetrofitContext().create(RelayEndpoints.class);
        }
        return relayService;
    }

    public static UserEndpoints getUserService(){
        if(userService == null){
            userService = getRetrofitContext().create(UserEndpoints.class);
        }
        return userService;
    }

    public static LightEndpoints getLightService(){
        if(lightService == null){
            lightService = getRetrofitContext().create(LightEndpoints.class);
        }
        return lightService;
    }
}
