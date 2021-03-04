package com.example.smarthome.relays.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class AddRelayFragment extends Fragment{

    private RelayViewModel viewModel;
    private ProgressBar progressBar;
    private FloatingActionButton fabAdd;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(RelayViewModel.class);
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
        progressBar = view.findViewById(R.id.relay_add_pb);
        fabAdd = view.findViewById(R.id.relay_add_fab);

        initViewModel();

        fabAdd.setOnClickListener(v -> {
            String name = etName.getText().toString();
            String ip = etIp.getText().toString();
            boolean on = switchOnOf.isChecked();

            Relay relay = new Relay(name, ip, on);

            viewModel.add(relay);
        });
    }

    private void initViewModel(){
        viewModel.getAddResult().observe(requireActivity(), bool -> {
            int pbVisibility = bool ? View.VISIBLE : View.GONE;
            int fabVisibility = bool ? View.GONE : View.VISIBLE;
            progressBar.setVisibility(pbVisibility);
            fabAdd.setVisibility(fabVisibility);
        });

        viewModel.getResponseMsg().observe(requireActivity(), str -> {
            if(!str.isEmpty()){
                Toast.makeText(requireActivity(), str, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
