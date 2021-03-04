package com.example.smarthome.relays.viewModels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.smarthome.relays.models.Relay;
import com.example.smarthome.relays.services.RelayService;

import java.util.List;

public class RelayViewModel extends AndroidViewModel{

    private final RelayService repository = RelayService.getInstance();

    private LiveData<List<Relay>> relaysLD;
    private LiveData<Boolean> progressBarLD;
    private LiveData<Boolean> addResultLD;
    private LiveData<String> responseMsgLD;
    private LiveData<Boolean> updateStatusLD;

    public RelayViewModel(Application application){
        super(application);

        relaysLD = repository.getRelaysLD();
        progressBarLD = repository.getProgressBarLD();
        addResultLD = repository.getAddProgressBarLD();
        responseMsgLD = repository.getResponseMsgLD();
        updateStatusLD = repository.getUpdateStatusLD();
    }

    public LiveData<List<Relay>> getRelaysLD(){return relaysLD;}
    public LiveData<Boolean> getProgressBarLD(){return progressBarLD;}
    public LiveData<Boolean> getAddResult(){return addResultLD;}
    public LiveData<String> getResponseMsg(){return responseMsgLD;}
    public LiveData<Boolean> getUpdateStatus(){return updateStatusLD;}


    public void refresh(){repository.getRelaysLD();}

    public void add(Relay relay){
        repository.addRelay(relay);
    }

    public void delete(Long id){
        repository.deleteById(id);}

    public LiveData<Relay> getRelayById(Long id){return repository.getRelayById(id);}

    public void turn(Long id){
        repository.turn(id);
    }

    public void update(Long id, Relay relay){
        repository.updateRelay(id, relay);
    }
}
