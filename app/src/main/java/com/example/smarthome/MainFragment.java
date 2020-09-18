package com.example.smarthome;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainFragment extends Fragment {

    public MainFragment(){}

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.main_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final Button bLights = view.findViewById(R.id.bLights);
        final Button bRelays = view.findViewById(R.id.bRelays);
        final FloatingActionButton fabAddDevice = view.findViewById(R.id.fab_add_device);

        fabAddDevice.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), AddDevicePagerActivity.class));
        });

        bLights.setOnClickListener(v -> {
            try{
                NavHostFragment.findNavController(MainFragment.this)
                        .navigate(R.id.action_viewLightsFragment);
            }
            catch (IllegalArgumentException e){
                e.printStackTrace();
            }
        });

        bRelays.setOnClickListener(v -> {
            try{
                NavHostFragment.findNavController(MainFragment.this)
                        .navigate(R.id.action_viewRelaysFragment);
            }catch (IllegalArgumentException e){
                e.printStackTrace();
            }
        });
    }
}
