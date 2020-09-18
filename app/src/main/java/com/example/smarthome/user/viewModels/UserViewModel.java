package com.example.smarthome.user.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.smarthome.user.models.User;
import com.example.smarthome.user.services.UserService;

public class UserViewModel extends ViewModel {

    private UserService service = UserService.getInstance();

    public MutableLiveData<String> getLoginMsg(){
        return service.getLoginMsg();
    }

    public MutableLiveData<String> getSignupMsg(){
        return service.getSignupMsg();
    }

    public void signin(User user){
        service.signin(user);
    }

    public void signup(User user){
        service.signup(user);
    }
}
