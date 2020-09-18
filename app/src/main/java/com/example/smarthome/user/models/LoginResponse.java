package com.example.smarthome.user.models;

import com.example.smarthome.BasicResponse;

//TODO change
public class LoginResponse extends BasicResponse<User> {
    public LoginResponse() {
    }

    public LoginResponse(User user, String msg) {
        super(user, msg);
    }
}
