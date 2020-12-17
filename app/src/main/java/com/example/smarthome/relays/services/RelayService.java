package com.example.smarthome.relays.services;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.smarthome.RetrofitContext;
import com.example.smarthome.relays.models.Relay;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RelayService {
    private final static IRelayRetrofitService service = RetrofitContext.getRelayService();

    private static RelayService instance;

    private static final String TAG = "Relay Service";

    private MutableLiveData<Boolean> progressBarLD = new MutableLiveData<>();
    private MutableLiveData<Boolean> addProgressBarLD = new MutableLiveData<>();
    private final MutableLiveData<List<Relay>> relaysLD = new MutableLiveData<>(new ArrayList<>());


    public LiveData<Boolean> getProgressBarLD(){
        return progressBarLD;
    }

    public LiveData<Boolean> getAddProgressBarLD(){
        return addProgressBarLD;
    }


    public static RelayService getInstance() {
        if(instance == null){
            instance = new RelayService();
        }
        return instance;
    }

    public void addRelay(Relay relay){
        Call<JsonObject> call = service.add(relay);

        addProgressBarLD.setValue(true);

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.i(TAG, "onResponse: addRelay");
                addProgressBarLD.setValue(false);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.i(TAG, "onFailure: addRelay");
                addProgressBarLD.setValue(false);
            }
        });
    }

    public void deleteById(Long id){
        Call<ResponseBody> call = service.deleteById(id);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    getRelaysLD();
                }
                else{
                    Log.i(TAG, "onResponse: Could not delete id: " + id);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.i(TAG, "Delete Failure");
            }
        });
    }

    public MutableLiveData<List<Relay>> getRelaysLD(){
        Call<List<Relay>> call = service.getAll();

        progressBarLD.setValue(true);

        call.enqueue(new Callback<List<Relay>>() {
            @Override
            public void onResponse(Call<List<Relay>> call, Response<List<Relay>> response) {
                Log.i(TAG, "onResponse: get all");                  //temp
                Log.i(TAG, "onResponse: body: " + response.body()); //temp
                List<Relay> relays = response.body();
                if(relays != null){
                    if(!relaysLD.getValue().equals(relays)) {
                        relaysLD.setValue(relays);
                    }
                }
                progressBarLD.setValue(false);
            }

            @Override
            public void onFailure(Call<List<Relay>> call, Throwable t) {
                Log.i(TAG, "onFailure: get all");
                progressBarLD.setValue(false);
            }
        });
        return relaysLD;
    }

    public LiveData<Relay> getRelayById(Long id){
        MutableLiveData<Relay> relayLD = new MutableLiveData<>();

        Call<Relay> call = service.getById(id);

        call.enqueue(new Callback<Relay>() {
            @Override
            public void onResponse(Call<Relay> call, Response<Relay> response) {
                if(response.isSuccessful()){
                    relayLD.setValue(response.body());
                    Log.i(TAG, "onResponse: " + relayLD.getValue());
                    Log.i(TAG, "onResponse: RelayId: " + relayLD.getValue().getId());
                }
            }

            @Override
            public void onFailure(Call<Relay> call, Throwable t) {

            }
        });
        return relayLD;
    }

    public void turn(Long id){
        Call<ResponseBody> call = service.turn(id);
        
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    Log.i(TAG, "onResponse: " + "switched");
                    getRelaysLD();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.i(TAG, "onFailure: could not switch, check WiFi or something");
            }
        });
        
    }
}
