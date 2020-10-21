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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
        final RecyclerView rvRelaysFound = view.findViewById(R.id.relays_found_rv);

        List<Relay> relays = new ArrayList<>();

        //Adding adapter to relays recycler view
        RelayRecyclerViewAdapter adapter = new RelayRecyclerViewAdapter(relays, requireActivity());
        rvRelaysFound.setAdapter(adapter);
        rvRelaysFound.setLayoutManager(new LinearLayoutManager(requireActivity()));

        model.getRelays();

        model.getRelaysLD().observe(getViewLifecycleOwner(), relayList -> {
            adapter.update(relayList);
            //TODO list view is not updating after data manipulation
        });

        bRefresh.setOnClickListener(v -> {
            model.getRelaysLD();
        });
    }
}
