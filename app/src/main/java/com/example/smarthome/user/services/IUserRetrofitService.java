package com.example.smarthome.user.services;

import com.example.smarthome.user.models.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface IUserRetrofitService {

    @POST("/user/signup")
    Call<String> signup(@Body User user);

    @POST("/user/signin")
    Call<String> signin(@Body User user);

}
