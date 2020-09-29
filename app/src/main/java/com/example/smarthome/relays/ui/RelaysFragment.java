package com.example.smarthome.relays.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.smarthome.R;
import com.example.smarthome.relays.models.Relay;
import com.example.smarthome.relays.viewModels.RelayViewModel;

import java.util.ArrayList;
import java.util.List;

public class RelaysFragment extends Fragment {

    private RelayViewModel model;

    public RelaysFragment(){}

    public static RelaysFragment newInstance() {
        return new RelaysFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        model = new ViewModelProvider(requireActivity()).get(RelayViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.relays_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final Button bRefresh = view.findViewById(R.id.relays_button_refresh);
        final ListView lvRelaysFound = view.findViewById(R.id.relays_found_lv);

        List<Relay> relays = new ArrayList<>();

        RelaysListAdapter adapter = new RelaysListAdapter(getActivity(), relays);
        adapter.update(relays);

        lvRelaysFound.setAdapter(adapter);

        model.getRelays();

        model.getRelaysLD().observe(getViewLifecycleOwner(), relayList -> {
            Log.i("Relays observer", "onViewCreated: " + relayList);
            adapter.update(relayList);
            //TODO list view is not updating after data manipulation
        });

        bRefresh.setOnClickListener(v -> {
            model.getRelaysLD();
        });
    }
}
