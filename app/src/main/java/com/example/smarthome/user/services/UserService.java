package com.example.smarthome.user.services;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.smarthome.RetrofitContext;
import com.example.smarthome.user.models.JwtToken;
import com.example.smarthome.user.models.LoginBody;
import com.example.smarthome.user.models.User;

import okhttp3.ResponseBody;
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
    private MutableLiveData<String> signupResponse = new MutableLiveData<>();
    private MutableLiveData<Boolean> isSignedIn = new MutableLiveData<>();
    private MutableLiveData<Boolean> showProgressBar = new MutableLiveData<>();


    public MutableLiveData<String> getLoginMsg(){return loginResponse;}
    public MutableLiveData<String> getSignupMsg(){return signupResponse;}
    public LiveData<Boolean> getSignedIn(){return isSignedIn;}
    public LiveData<Boolean> getShowProgressBar(){return showProgressBar;}


    public void signup(User user){
        Call<String> call = service.signup(user);

        showProgressBar.setValue(true);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                signupResponse.setValue("Response");
                signupResponse.setValue("");
                showProgressBar.setValue(false);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                signupResponse.setValue("Error connecting to server");
                signupResponse.setValue("");
                showProgressBar.setValue(false);
            }
        });
    }
    
    public void signin(LoginBody user){
        Call<ResponseBody> call = service.signin(user);

        showProgressBar.setValue(true);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful() && response.code() == 200){
                    User.Companion.setSignedIn(true);
                    isSignedIn.setValue(true);
                    String token = response.headers().get("Authorization");
                    JwtToken.Companion.setJwtToken(token);
                }
                else{
                    User.Companion.setSignedIn(false);
                    isSignedIn.setValue(false);
                }
                loginResponse.setValue(response.message());
                loginResponse.setValue("");

                showProgressBar.setValue(false);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                User.Companion.setSignedIn(false);
                isSignedIn.setValue(false);
                loginResponse.setValue("Error connecting to server");
                loginResponse.setValue("");

                showProgressBar.setValue(false);
            }
        });
    }
}
