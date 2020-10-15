package com.example.smarthome.user.services;

import com.example.smarthome.user.models.LoginResponse;
import com.example.smarthome.user.models.SigninBody;
import com.example.smarthome.user.models.User;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface IUserRetrofitService {

    @POST("/user/signup")
    Call<String> signup(@Body User user);

    @POST("/user/signin")
    Call<ResponseBody> signin(@Body SigninBody signinBody);

}
