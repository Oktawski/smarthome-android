package com.example.smarthome.relays.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.smarthome.relays.models.Relay;
import com.example.smarthome.relays.services.RelayService;

import java.util.List;

public class RelayViewModel extends ViewModel{

    private RelayService service = RelayService.getInstance();

    private MutableLiveData<Boolean> progressBarLD;

    public MutableLiveData<List<Relay>> getRelaysLD(){
        return service.getRelaysLD();
    }

    public LiveData<Boolean> getProgressBarLD(){
        return service.getProgressBarLD();
    }

    public LiveData<Boolean> getAddResult(){
        return service.getAddProgressBarLD();
    }

    public void add(Relay relay){
        service.addRelay(relay);
    }

    public void delete(Long id){service.deleteById(id);}

    public LiveData<Relay> getRelayById(Long id){return service.getRelayById(id);}

    public void turn(Long id){
        service.turn(id);
    }
}
