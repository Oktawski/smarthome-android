package com.example.smarthome.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smarthome.R;
import com.example.smarthome.WifiDevice;
import com.example.smarthome.relays.models.Relay;
import com.example.smarthome.relays.viewModels.RelayViewModel;
import com.google.android.material.switchmaterial.SwitchMaterial;

import java.util.List;

public abstract class GenericRVAdapter<T extends WifiDevice>
    extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private final Context context;
    private final List<T> items;


    public GenericRVAdapter(Context context, List<T> items){
        this.context = context;
        this.items = items;
    }

    public void update(List<T> list){
        this.items.clear();
        this.items.addAll(list);
        notifyDataSetChanged();
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

        return new RelayViewHolder(v, new ViewModelProvider((FragmentActivity)context).get(RelayViewModel.class));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        throw new UnsupportedOperationException();

    }

    @Override
    public int getItemCount() {
       return items.size();
    }

    public class RelayViewHolder extends RecyclerView.ViewHolder {
        private boolean isExpanded = false;

        private final TextView tvName;
        private final SwitchMaterial switchMaterial;
        private final ConstraintLayout expandableLayout;
        private final AppCompatImageButton deleteIcon, editIcon;

        private final RelayViewModel viewModel;

        public RelayViewHolder(@NonNull View itemView, RelayViewModel viewModel) {
            super(itemView);

            tvName = itemView.findViewById(R.id.relay_item_name);
            switchMaterial = itemView.findViewById(R.id.relay_item_slider);
            expandableLayout = itemView.findViewById(R.id.item_relay_expandable_view);
            deleteIcon = itemView.findViewById(R.id.item_relay_delete_icon);
            editIcon = itemView.findViewById(R.id.item_relay_edit_icon);

            this.viewModel = viewModel;
        }

        public void setDetails(Relay relay) {
            tvName.setText(relay.getName());
            switchMaterial.setChecked(relay.getOn());
            switchMaterial.setClickable(false);

            itemView.setOnClickListener(v -> {
                isExpanded = !isExpanded;
                expandableLayout.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
            });

            deleteIcon.setOnClickListener(v -> viewModel.delete(relay.getId()));

            // TODO implement edit icon
            editIcon.setOnClickListener(v ->
                    Toast.makeText(context, "IMPLEMENT ME", Toast.LENGTH_SHORT).show());

            switchMaterial.setOnClickListener(v -> viewModel.turn(relay.getId()));
        }
    }
}

