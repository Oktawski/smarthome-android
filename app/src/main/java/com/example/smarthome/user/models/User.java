package com.example.smarthome.user.models;

import com.example.smarthome.relays.models.Relay;

import java.util.List;

public class User {

    private Long id;
    private String username;
    private String email;
    private String password;
    private List<Relay> relayList;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Relay> getRelayList() {
        return relayList;
    }

    public void setRelayList(List<Relay> relayList) {
        this.relayList = relayList;
    }
}
