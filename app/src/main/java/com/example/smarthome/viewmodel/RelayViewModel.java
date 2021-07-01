package com.example.smarthome.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.smarthome.data.model.Relay;
import com.example.smarthome.data.api.RelayService;
import com.example.smarthome.utilities.Resource;

import java.util.List;

public class RelayViewModel extends ViewModel {

    private final RelayService repository = RelayService.getInstance();



    public LiveData<List<Relay>> getRelays(){ return repository.fetchRelays(); }
    //public LiveData<List<Relay>> getRelaysLD(){return repository.getRelays(); }
    public LiveData<Resource<Relay>> getStatus(){return repository.getStatus(); }


    public void refresh(){
        repository.fetchRelays();
    }

    public void add(Relay relay){
        repository.add(relay);
    }

    public void delete(Long id){
        repository.deleteById(id);}

    public LiveData<Relay> getRelayById(Long id){
        return repository.getRelayById(id);
    }

    public void turn(Long id){
        repository.turn(id);
    }

    public void update(Long id, Relay relay){
        repository.updateRelay(id, relay);
    }
}
