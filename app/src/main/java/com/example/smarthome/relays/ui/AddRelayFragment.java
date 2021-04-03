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
    private EditText etName;
    private EditText etIp;
    private SwitchMaterial switchOnOf;

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

        etName = view.findViewById(R.id.relay_add_et_name);
        etIp = view.findViewById(R.id.relay_add_et_ip);
        switchOnOf = view.findViewById(R.id.relay_add_switch_onof);
        progressBar = view.findViewById(R.id.relay_add_pb);
        fabAdd = view.findViewById(R.id.relay_add_fab);

        initViewModelObservables();
        initOnClickListeners();
    }

    private void initViewModelObservables(){
        viewModel.getStatus().observe(getViewLifecycleOwner(), status -> {

            String message = "";

            switch(status.getStatus()){
                case LOADING:
                    progressBar.setVisibility(View.VISIBLE);
                    fabAdd.setVisibility(View.GONE);
                    break;
                default:
                    progressBar.setVisibility(View.GONE);
                    fabAdd.setVisibility(View.VISIBLE);
                    if(status.getMessage() != null) message = status.getMessage();

                    if(!message.isEmpty())
                        Toast.makeText(
                                requireActivity(), status.getMessage(), Toast.LENGTH_SHORT).show();
                    break;
            }
        });
    }

    private void initOnClickListeners(){
        fabAdd.setOnClickListener(v -> {
            String name = etName.getText().toString();
            String ip = etIp.getText().toString();
            boolean on = switchOnOf.isChecked();

            Relay relay = new Relay(name, ip, on);

            viewModel.add(relay);
        });
    }
}
