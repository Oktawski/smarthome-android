package com.example.smarthome;

import com.example.smarthome.relays.models.Relay;

public interface IViewModel<T extends Relay> {
    void add(T t);
    void deleteById(Long id);
    void getAll();
}
