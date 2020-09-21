package com.example.smarthome.ligths.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.smarthome.R;
import com.example.smarthome.ligths.viewModels.LightViewModel;
import com.google.android.material.switchmaterial.SwitchMaterial;

public class AddLightFragment extends Fragment {

    private LightViewModel model;

    public AddLightFragment(){}

    public static AddLightFragment newInstance() {

        Bundle args = new Bundle();

        AddLightFragment fragment = new AddLightFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        model = new ViewModelProvider(requireActivity()).get(LightViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.add_light_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final EditText etName = view.findViewById(R.id.light_add_et_name);
        final EditText etIp = view.findViewById(R.id.light_add_et_ip);
        final SwitchMaterial switchOnOf = view.findViewById(R.id.light_add_switch_onof);
        final Button bAdd = view.findViewById(R.id.light_add_button_Add);

        bAdd.setOnClickListener(v -> {
            String name = etName.getText().toString();
            String ip = etIp.getText().toString();
            boolean on = switchOnOf.isChecked();

            //todo send light to server
        });
    }
}
