package com.example.smarthome.user.viewModels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.smarthome.user.models.SigninBody;
import com.example.smarthome.user.models.User;
import com.example.smarthome.user.services.UserService;

public class UserViewModel extends ViewModel {

    private UserService service = UserService.getInstance();

    public MutableLiveData<String> getSignupMsg(){
        return service.getSignupMsg();
    }
    public MutableLiveData<String> getLoginMsg(){
        return service.getLoginMsg();
    }
    public LiveData<Boolean> getIsSignedIn(){return service.getSignedIn();}
    public LiveData<Boolean> showProgressBar(){return service.getShowProgressBar();}


    public void signin(SigninBody user){
        service.signin(user);
    }

    public void signup(User user){
        service.signup(user);
    }
}
