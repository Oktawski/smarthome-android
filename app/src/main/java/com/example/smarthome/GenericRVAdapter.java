package com.example.smarthome;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;


import com.example.smarthome.relays.models.Relay;
import com.example.smarthome.relays.viewModels.RelayViewModel;
import com.google.android.material.switchmaterial.SwitchMaterial;

import java.util.List;

public abstract class GenericRVAdapter<T extends WifiDevice>
    extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context context;
    private List<T> list;

    public abstract void onBindData(RecyclerView.ViewHolder viewHolder, T t);
    public abstract RecyclerView.ViewHolder setViewHolder(ViewGroup parent);

    public GenericRVAdapter(Context context, List<T> list){
        this.context = context;
        this.list = list;
    }

    public void update(List<T> list){
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = setViewHolder(parent);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        onBindData(holder, list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public T getItem(int position){
        return list.get(position);
    }

    public class RelayViewHolder extends RecyclerView.ViewHolder {
        private Long relayId;

        private boolean isExtended = false;

        private TextView etName;
        private SwitchMaterial switchMaterial;
        private ConstraintLayout expandableLayout;
        private AppCompatImageButton deleteIcon, editIcon;

        private RelayViewModel viewModel;

        public RelayViewHolder(Context context, @NonNull View itemView) {
            super(itemView);

            etName = itemView.findViewById(R.id.relay_item_name);
            switchMaterial = itemView.findViewById(R.id.relay_item_slider);
            expandableLayout = itemView.findViewById(R.id.item_relay_expandable_view);
            deleteIcon = itemView.findViewById(R.id.item_relay_delete_icon);
        }

        public void setDetails(Relay relay, RelayViewModel viewModel){
            this.viewModel = viewModel;

            etName.setText(relay.getName());
            switchMaterial.setChecked(relay.getOn());
            switchMaterial.setClickable(false);
            isExtended = false;

            etName.setOnClickListener(v -> {
                isExtended = !isExtended;
                expandableLayout.setVisibility(isExtended ? View.VISIBLE : View.GONE);
            });

            deleteIcon.setOnClickListener(v -> this.viewModel.delete(relayId));

            switchMaterial.setOnClickListener(v -> this.viewModel.turn(relayId));
        }
    }
}

