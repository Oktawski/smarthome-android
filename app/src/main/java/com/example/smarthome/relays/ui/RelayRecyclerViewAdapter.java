package com.example.smarthome.relays.ui;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smarthome.R;
import com.example.smarthome.relays.models.Relay;
import com.example.smarthome.relays.viewModels.RelayViewModel;
import com.google.android.material.switchmaterial.SwitchMaterial;

import java.util.List;

public class RelayRecyclerViewAdapter
        extends RecyclerView.Adapter<RelayRecyclerViewAdapter.ViewHolder> {

    private List<Relay> relays;
    private Context context;
    private RelayViewModel model;

    public RelayRecyclerViewAdapter(List<Relay> relays, Context context) {
        this.relays = relays;
        this.context = context;
        this.model = new ViewModelProvider((FragmentActivity) context).get(RelayViewModel.class);
    }

    public void update(List<Relay> relayList){
        relays.clear();
        relays.addAll(relayList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context)
                .inflate(R.layout.item_relay, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Relay relay = relays.get(position);
        holder.relayId = relay.getId();

        holder.etName.setText(relay.getName());
        holder.switchMaterial.setChecked(relay.getOn());
        holder.switchMaterial.setClickable(false);

        holder.switchMaterial.setOnClickListener(v -> model.turn(relay.getId()));
    }

    @Override
    public int getItemCount() {
        return relays.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private Long relayId;

        /*
        *TODO stick "isExtended" to only one item
        * currently when extended item is removed, the item above it will become extended
        * (21.01.2021)
        */
        private boolean isExtended = false;

        private TextView etName;
        private SwitchMaterial switchMaterial;
        private ConstraintLayout expandableLayout;
        private AppCompatImageButton deleteIcon, editIcon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            etName = itemView.findViewById(R.id.relay_item_name);
            switchMaterial = itemView.findViewById(R.id.relay_item_slider);
            expandableLayout = itemView.findViewById(R.id.item_relay_expandable_view);
            deleteIcon = itemView.findViewById(R.id.item_relay_delete_icon);
            editIcon = itemView.findViewById(R.id.item_relay_edit_icon);


            itemView.setOnClickListener(v -> {
                Log.i("RELAY CLICKED", String.format("Name: %s", etName.getText().toString()));
                model.getRelayById(relayId);
                Log.i("RELAY FROM ITEM VIEW", "Name:  " + relayId);

                isExtended = !isExtended;
                expandableLayout.setVisibility(isExtended ? View.VISIBLE : View.GONE);
            });

            deleteIcon.setOnClickListener(v -> model.delete(relayId));

            editIcon.setOnClickListener(v -> model.getRelayById(relayId));
        }
    }
}
