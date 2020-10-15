package com.example.smarthome.user.models;

public class JwtToken {

    private static String token;

    public static String getToken() {
        return token != null ? token : "";
    }

    public static void setToken(String token) {
        JwtToken.token = token;
    }
}
