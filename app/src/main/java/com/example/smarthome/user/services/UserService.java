package com.example.smarthome.user.services;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.smarthome.RetrofitContext;
import com.example.smarthome.user.models.LoginResponse;
import com.example.smarthome.user.models.SignupResponse;
import com.example.smarthome.user.models.User;

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

    private MutableLiveData<String> loginMsg = new MutableLiveData<>();
    private MutableLiveData<String> signupMsg = new MutableLiveData<>();

    public MutableLiveData<String> getLoginMsg(){return loginMsg;}
    public MutableLiveData<String> getSignupMsg(){return signupMsg;}


    public void signup(User user){
        Call<SignupResponse> call = service.signup(user);

        signupMsg.setValue("");
        
        call.enqueue(new Callback<SignupResponse>() {
            @Override
            public void onResponse(Call<SignupResponse> call, Response<SignupResponse> response) {
                if(response.isSuccessful()){
                    signupMsg.setValue(response.body().getMsg());
                }
                else{
                    signupMsg.setValue("Error");
                    Log.i(TAG, "onResponse: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<SignupResponse> call, Throwable t) {
                signupMsg.setValue("Problem connecting to server");
            }
        });
    }
    
    public void signin(User user){
        Call<LoginResponse> call = service.signin(user);

        loginMsg.setValue("");
    
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if(response.isSuccessful()) {
                    loginMsg.setValue("Hello");
                }
                else{
                    loginMsg.setValue("Could not sign in...");
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                loginMsg.setValue("Problem connecting to server");
            }
        });
    }
}
