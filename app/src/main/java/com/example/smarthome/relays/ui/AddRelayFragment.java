package com.example.smarthome.relays.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.smarthome.R;
import com.example.smarthome.relays.models.Relay;
import com.example.smarthome.relays.viewModels.RelayViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.switchmaterial.SwitchMaterial;

public class AddRelayFragment extends Fragment {

    private RelayViewModel model;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        model = new ViewModelProvider(requireActivity()).get(RelayViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.add_relay_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        final EditText etName = view.findViewById(R.id.relay_add_et_name);
        final EditText etIp = view.findViewById(R.id.relay_add_et_ip);
        final SwitchMaterial switchOnOf = view.findViewById(R.id.relay_add_switch_onof);
        final ProgressBar progressBar = view.findViewById(R.id.relay_add_pb);
        final FloatingActionButton fabAdd = view.findViewById(R.id.relay_add_fab);


        model.getAddResult().observe(requireActivity(), bool -> {
            int visibility = bool ? View.VISIBLE : View.GONE;
            progressBar.setVisibility(visibility);
            fabAdd.setVisibility(bool ? View.GONE : View.VISIBLE);
            /*
            if (bool) {
                fabAdd.hide();
            } else {
                fabAdd.show();
            }*/
        });

        model.getResponseMsg().observe(requireActivity(), str -> {
            if(str != "") {
                Toast.makeText(requireActivity(), str, Toast.LENGTH_SHORT).show();
            }
        });

        fabAdd.setOnClickListener(v -> {
            String name = etName.getText().toString();
            String ip = etIp.getText().toString();
            boolean on = switchOnOf.isChecked();

            Relay relay = new Relay(name, ip, on);

            model.add(relay);
        });
    }
}
