package com.example.smarthome.relays.ui;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.smarthome.MainActivity;
import com.example.smarthome.R;
import com.example.smarthome.WifiDevice;
import com.example.smarthome.relays.models.Relay;
import com.example.smarthome.relays.viewModels.RelayViewModel;
import com.google.android.material.slider.Slider;
import com.google.android.material.switchmaterial.SwitchMaterial;

import java.util.List;

public class RelaysListAdapter extends ArrayAdapter<Relay> {

    private RelayViewModel model;

    public RelaysListAdapter(Context context, List<Relay> relays) {
        super(context, 0, relays);

        model = new ViewModelProvider((FragmentActivity) context).get(RelayViewModel.class);
    }

    public void update(List<Relay> relays){
        this.clear();
        this.addAll(relays);
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Relay relay = getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.item_relay, parent, false);
        }

        final TextView etName = convertView.findViewById(R.id.relay_item_name);
        final SwitchMaterial switchMaterial = convertView.findViewById(R.id.relay_item_slider);

        etName.setText(relay.getName());
        switchMaterial.setChecked(relay.getOn());


        switchMaterial.setOnClickListener(v -> {
            model.turn(relay.getId());
            //TODO don't switch switch if there is no connection or failure from response
        });

        convertView.setOnClickListener(v -> {
            Log.i("Adapter click", "onClick: " + relay.getName());
            //TODO details
        });

        return convertView;
    }
}
