package com.example.smarthome.relays.ui;

import android.content.Context;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
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

        holder.etName.setText(relay.getName());
        holder.switchMaterial.setChecked(relay.getOn());
        holder.switchMaterial.setClickable(false);

        holder.switchMaterial.setOnClickListener(v -> {
            model.turn(relay.getId());
        });
    }

    @Override
    public int getItemCount() {
        return relays.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView etName;
        private SwitchMaterial switchMaterial;
        private ConstraintLayout expandableLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            etName = itemView.findViewById(R.id.relay_item_name);
            switchMaterial = itemView.findViewById(R.id.relay_item_slider);
            //expandableLayout = itemView.findViewById(R.id.item_relay_expandable_view);
        }
    }
}
