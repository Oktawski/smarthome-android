package com.example.smarthome.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smarthome.R;
import com.example.smarthome.WifiDevice;
import com.example.smarthome.ligths.viewModels.LightViewModel;
import com.example.smarthome.relays.models.Relay;
import com.example.smarthome.relays.ui.DetailsRelayActivity;
import com.example.smarthome.relays.viewModels.RelayViewModel;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.switchmaterial.SwitchMaterial;

import java.util.List;

public abstract class GenericRVAdapter<T extends WifiDevice>
    extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private final Context context;
    public List<T> items;


    public GenericRVAdapter(Context context, List<T> items){
        this.context = context;
        this.items = items;
    }

    public void update(List<T> list){
        this.items.clear();
        this.items.addAll(list);
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return this.items.get(position).getViewType();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context)
                .inflate(viewType, parent, false);

        switch(viewType){
            case R.layout.item_relay:
                return new RelayViewHolder(v, new ViewModelProvider((FragmentActivity)context).get(RelayViewModel.class));
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getItemCount() {
       return items.size();
    }

    // Relay View Holder
    public class RelayViewHolder extends RecyclerView.ViewHolder {
        private boolean isExpanded = false;

        private final TextView tvName, ipDescription;
        private final SwitchMaterial switchMaterial;
        private final LinearLayout expandableLayout;
        private final AppCompatImageButton deleteIcon, editIcon;

        private final RelayViewModel viewModel;

        public RelayViewHolder(@NonNull View itemView, RelayViewModel viewModel) {
            super(itemView);

            tvName = itemView.findViewById(R.id.relay_item_name);
            switchMaterial = itemView.findViewById(R.id.relay_item_slider);
            expandableLayout = itemView.findViewById(R.id.item_relay_expandable_view);
            ipDescription = itemView.findViewById(R.id.item_relay_ip_description);
            deleteIcon = itemView.findViewById(R.id.item_relay_delete_button);
            editIcon = itemView.findViewById(R.id.item_relay_edit_button);

            this.viewModel = viewModel;
        }

        public void setDetails(Relay relay) {
            tvName.setText(relay.getName());
            switchMaterial.setChecked(relay.getOn());
            switchMaterial.setClickable(false);

            ipDescription.setText(relay.getIp());

            itemView.setOnClickListener(v -> {
                isExpanded = !isExpanded;
                expandableLayout.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
            });

            deleteIcon.setOnClickListener(v -> {
                Snackbar snackbar = Snackbar.make(v,
                            "Do you want to delete relay: " + relay.getName(),
                            Snackbar.LENGTH_INDEFINITE
                        )
                        .setDuration(5000)
                        .setAction("Yes", a -> viewModel.delete(relay.getId()))
                        .setTextColor(Color.WHITE)
                        .setActionTextColor(ContextCompat.getColor(context, R.color.colorPrimary));

                snackbar.getView().setBackgroundColor(ContextCompat.getColor(context, R.color.actionDarkBackground));

                snackbar.show();
            });

            // TODO implement edit icon
            editIcon.setOnClickListener(v -> {
                Bundle bundle = new Bundle();
                bundle.putString("name", tvName.getText().toString());
                bundle.putString("ip", ipDescription.getText().toString());

                Intent intent = new Intent(context, DetailsRelayActivity.class);
                intent.putExtras(bundle);

                context.startActivity(intent);
            });

            switchMaterial.setOnClickListener(v -> viewModel.turn(relay.getId()));
        }
    }

    public class LightViewHolder extends RecyclerView.ViewHolder{
        public LightViewHolder(@NonNull View itemView, LightViewModel viewModel) {
            super(itemView);
        }
    }
}

