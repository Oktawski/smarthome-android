package com.example.smarthome.relays.services;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.smarthome.BasicResponse;
import com.example.smarthome.RetrofitContext;
import com.example.smarthome.relays.models.Relay;
import com.example.smarthome.utilities.Resource;
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

    public static RelayService getInstance() {
        if(instance == null){
            instance = new RelayService();
        }
        return instance;
    }

    private final MutableLiveData<List<Relay>> relaysLD = new MutableLiveData<>(new ArrayList<>());
    private final MutableLiveData<Resource<Relay>> resourceStatusLD = new MutableLiveData<>();

    public LiveData<Resource<Relay>> getStatus(){return resourceStatusLD;}


    public void addRelay(Relay relay){
        Call<BasicResponse<Relay>> call = service.add(relay);

        resourceStatusLD.setValue(Resource.Companion.loading(null));

        call.enqueue(new Callback<BasicResponse<Relay>>() {
            @Override
            public void onResponse(Call<BasicResponse<Relay>> call, Response<BasicResponse<Relay>> response) {
                if(response.isSuccessful()){
                    resourceStatusLD.setValue(
                            Resource.Companion.success(null, response.body().getMsg()));
                    getRelaysLD();
                }
                else{
                    Gson gson = new Gson();
                    Type type = new TypeToken<BasicResponse<Relay>>() {}.getType();
                    BasicResponse<Relay> errorResponse =
                            gson.fromJson(response.errorBody().charStream(), type);

                    resourceStatusLD.setValue(
                            Resource.Companion.error(errorResponse.getMsg(), null));
                }
            }

            @Override
            public void onFailure(Call<BasicResponse<Relay>> call, Throwable t) {
                Log.i(TAG, "onFailure: addRelay");
                resourceStatusLD.setValue(
                        Resource.Companion.error("Could not connect to server", null));
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

        resourceStatusLD.setValue(Resource.Companion.loading(null));

        call.enqueue(new Callback<List<Relay>>() {
            @Override
            public void onResponse(Call<List<Relay>> call, Response<List<Relay>> response) {
                Log.i(TAG, "onResponse: get all");                  //temp
                Log.i(TAG, "onResponse: body: " + response.body()); //temp
                List<Relay> relays = response.body();
                if(relays != null){
                    relaysLD.setValue(relays);
                }
                resourceStatusLD.setValue(Resource.Companion.success(null, null));
            }

            @Override
            public void onFailure(Call<List<Relay>> call, Throwable t) {
                Log.i(TAG, "onFailure: get all");
                resourceStatusLD.setValue(
                        Resource.Companion.error("Could not connect to server", null));
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

        resourceStatusLD.setValue(Resource.Companion.loading(null));

        call.enqueue(new Callback<Relay>() {
            @Override
            public void onResponse(Call<Relay> call, Response<Relay> response) {
                if(response.isSuccessful()){
                    resourceStatusLD.setValue(
                            Resource.Companion.success(null, "Updated"));

                    getRelaysLD();
                }
            }

            @Override
            public void onFailure(Call<Relay> call, Throwable t) {
                resourceStatusLD.setValue(
                        Resource.Companion.error("Could not connect to server", null));
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
