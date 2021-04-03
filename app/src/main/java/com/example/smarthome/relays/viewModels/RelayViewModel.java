package com.example.smarthome.relays.viewModels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.smarthome.relays.models.Relay;
import com.example.smarthome.relays.services.RelayService;
import com.example.smarthome.utilities.Resource;

import java.util.List;

public class RelayViewModel extends AndroidViewModel{

    private final RelayService repository = RelayService.getInstance();

    public RelayViewModel(Application application){
        super(application);
    }

    public LiveData<List<Relay>> getRelaysLD(){return repository.getRelaysLD();}
    public LiveData<Resource<Relay>> getStatus(){return repository.getStatus();}


    public void refresh(){
        repository.getRelaysLD();
    }

    public void add(Relay relay){
        repository.addRelay(relay);
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
