package com.example.smarthome.user.services;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.smarthome.RetrofitContext;
import com.example.smarthome.user.models.JwtToken;
import com.example.smarthome.user.models.LoginRequest;
import com.example.smarthome.user.models.User;
import com.example.smarthome.utilities.Resource;

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

    private final MutableLiveData<Boolean> isSignedIn = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> serverStatus = new MutableLiveData<>();
    private final MutableLiveData<Resource<User>> statusLD = new MutableLiveData<>();


    public LiveData<Boolean> getSignedIn(){return isSignedIn;}
    public LiveData<Boolean> getServerStatusLD(){return serverStatus;}
    public LiveData<Resource<User>> getStatus(){return statusLD;}


    // TODO make responses correspond to server responses
    public void signup(User user){
        Call<ResponseBody> call = service.signup(user);

        statusLD.setValue(Resource.Companion.loading(null));

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.code() == 200){
                    statusLD.setValue(Resource.Companion.success(user, "Account created"));
                }
                else{
                    statusLD.setValue(
                            Resource.Companion.error("Something went wrong", null));
                }
                clearMessage();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                statusLD.setValue(
                        Resource.Companion.error("Error connecting to server", null));
            }
        });
    }

    // TODO make responses correspond to server responses
    public void signin(LoginRequest user){
        Call<ResponseBody> call = service.signin(user);

        statusLD.setValue(Resource.Companion.loading(null));

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful() && response.code() == 200){
                    User.Companion.setSignedIn(true);
                    String token = response.headers().get("Authorization");
                    JwtToken.Companion.setJwtToken(token);

                    String message = "Welcome " + user.getUsername();

                    isSignedIn.setValue(true);
                    statusLD.setValue(Resource.Companion.success(null, message));
                }
                else{
                    User.Companion.setSignedIn(false);
                    JwtToken.Companion.clear();

                    isSignedIn.setValue(false);
                    statusLD.setValue(Resource.Companion.error("Error", null));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                User.Companion.setSignedIn(false);
                isSignedIn.setValue(false);

                statusLD.setValue(
                        Resource.Companion.error("Error connecting to server", null));
            }
        });
    }

    public void getServerStatus(){
        Call<ResponseBody> call = service.getStatus();

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    serverStatus.setValue(true);
                }
                else{
                    serverStatus.setValue(false);
                    signOut();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                serverStatus.setValue(false);
                signOut();
            }
        });
    }

    public void signOut(){
        User.Companion.setSignedIn(false);
        JwtToken.Companion.clear();

        isSignedIn.setValue(false);
        clearMessage();
    }

    private void clearMessage(){
        statusLD.setValue(Resource.Companion.success(null, null));
    }
}
