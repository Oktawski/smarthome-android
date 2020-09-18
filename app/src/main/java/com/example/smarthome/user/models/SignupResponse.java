package com.example.smarthome.user.models;

import com.example.smarthome.BasicResponse;

public class SignupResponse extends BasicResponse<User> {

    public SignupResponse() {
    }

    public SignupResponse(User user, String msg) {
        super(user, msg);
    }
}
