package com.example.smarthome.relays.services;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.smarthome.BasicResponse;
import com.example.smarthome.RetrofitContext;
import com.example.smarthome.relays.models.Relay;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
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

    private final MutableLiveData<Boolean> progressBarLD = new MutableLiveData<>();
    private final MutableLiveData<Boolean> addProgressBarLD = new MutableLiveData<>();
    private final MutableLiveData<List<Relay>> relaysLD = new MutableLiveData<>(new ArrayList<>());
    private final MutableLiveData<String> responseMsgLD = new MutableLiveData<>();
    private final MutableLiveData<Boolean> updateStatusLD = new MutableLiveData<>();


    public LiveData<Boolean> getProgressBarLD(){
        return progressBarLD;
    }
    public LiveData<Boolean> getAddProgressBarLD(){
        return addProgressBarLD;
    }
    public LiveData<String> getResponseMsgLD() {return responseMsgLD;}
    public LiveData<Boolean> getUpdateStatusLD() {return updateStatusLD;}


    public static RelayService getInstance() {
        if(instance == null){
            instance = new RelayService();
        }
        return instance;
    }

    public void addRelay(Relay relay){
        Call<BasicResponse<Relay>> call = service.add(relay);

        addProgressBarLD.setValue(true);

        call.enqueue(new Callback<BasicResponse<Relay>>() {
            @Override
            public void onResponse(Call<BasicResponse<Relay>> call, Response<BasicResponse<Relay>> response) {
                if(response.isSuccessful()){
                    responseMsgLD.setValue(response.body().getMsg());
                    Log.i(TAG, "onResponse: " + response.body().getMsg());
                    getRelaysLD();
                }
                else{
                    Gson gson = new Gson();
                    Type type = new TypeToken<BasicResponse<Relay>>() {}.getType();
                    BasicResponse<Relay> errorResponse = gson.fromJson(response.errorBody().charStream(), type);
                    responseMsgLD.setValue(errorResponse.getMsg());
                }

                responseMsgLD.setValue("");
                addProgressBarLD.setValue(false);
            }

            @Override
            public void onFailure(Call<BasicResponse<Relay>> call, Throwable t) {
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
                    relaysLD.setValue(relays);
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

    public void updateRelay(Long id, Relay relay){
        Call<Relay> call = service.updateById(id, relay);

        call.enqueue(new Callback<Relay>() {
            @Override
            public void onResponse(Call<Relay> call, Response<Relay> response) {
                if(response.isSuccessful()){
                    updateStatusLD.setValue(true);
                    getRelaysLD();
                }
                updateStatusLD.setValue(false);
            }

            @Override
            public void onFailure(Call<Relay> call, Throwable t) {
                updateStatusLD.setValue(false);
            }
        });
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
