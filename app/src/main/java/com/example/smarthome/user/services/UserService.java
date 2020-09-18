package com.example.smarthome.user.services;

import android.text.method.MultiTapKeyListener;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.smarthome.RetrofitContext;
import com.example.smarthome.user.models.LoginResponse;
import com.example.smarthome.user.models.SignupResponse;
import com.example.smarthome.user.models.User;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserService {

    private final static IUserRetrofitService service = RetrofitContext.getUserService();
    private static UserService instance;
    private final static String TAG = "User service";

    public static UserService getInstance() {
        if(instance == null){
            instance = new UserService();
        }
        return instance;
    }

    private MutableLiveData<String> loginResponse = new MutableLiveData<>();
    MutableLiveData<String> signupResponse = new MutableLiveData<>();


    public MutableLiveData<String> getLoginMsg(){return loginResponse;}
    public MutableLiveData<String> getSignupMsg(){return signupResponse;}


    public void signup(User user){
        Call<String> call = service.signup(user);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                signupResponse.setValue("Response");
                signupResponse.setValue("");
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                signupResponse.setValue("Error connecting to server");
                signupResponse.setValue("");
            }
        });
    }
    
    public void signin(User user){
        Call<String> call = service.signin(user);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                loginResponse.setValue("Response");
                loginResponse.setValue("");
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                loginResponse.setValue("Error connecting to server");
                loginResponse.setValue("");
            }
        });
    }
}
