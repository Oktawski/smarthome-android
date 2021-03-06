package com.example.smarthome.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
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
import com.example.smarthome.relays.services.IRelayRetrofitService;
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
    public class RelayViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        private boolean isExpanded = false;

        private final TextView tvName, ipDescription;
        private final SwitchMaterial switchMaterial;
        private final LinearLayout expandableLayout;
        private final AppCompatImageButton deleteIcon, editIcon, expandArrow;

        private Relay relay;

        private final RelayViewModel viewModel;

        public RelayViewHolder(@NonNull View itemView, RelayViewModel viewModel) {
            super(itemView);

            tvName = itemView.findViewById(R.id.relay_item_name);
            switchMaterial = itemView.findViewById(R.id.relay_item_slider);
            expandableLayout = itemView.findViewById(R.id.item_relay_expandable_view);
            ipDescription = itemView.findViewById(R.id.item_relay_ip_description);
            deleteIcon = itemView.findViewById(R.id.item_relay_delete_button);
            editIcon = itemView.findViewById(R.id.item_relay_edit_button);
            expandArrow = itemView.findViewById(R.id.relay_item_expand_arrow);

            this.viewModel = viewModel;

            itemView.setOnCreateContextMenuListener(this);
        }

        public void setDetails(Relay relay) {
            tvName.setText(relay.getName());
            tvName.setWidth(((View)tvName.getParent()).getWidth() / 2);
            switchMaterial.setChecked(relay.getOn());
            switchMaterial.setClickable(false);
            ipDescription.setText(relay.getIp());
            this.relay = relay;

            setOncLickListeners();
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle(relay.getName() + " Relay");
            MenuItem edit = menu.add(Menu.FIRST, 0, Menu.NONE, "Edit");
            MenuItem delete = menu.add(Menu.FIRST, 1, Menu.NONE, "Delete");

            edit.setOnMenuItemClickListener(v -> {
                edit();
                return true;
            });

            delete.setOnMenuItemClickListener(v -> {
                delete(view);
                return true;
            });
        }

        private void setOncLickListeners(){
            switchMaterial.setOnClickListener(v -> {
                viewModel.turn(relay.getId());
            });

            expandableLayout.setVisibility(isExpanded ? View.VISIBLE : View.GONE);

            itemView.setOnClickListener(v -> {
                isExpanded = !isExpanded;
                expandableLayout.setVisibility(isExpanded ? View.VISIBLE : View.GONE);

            });

            itemView.setOnLongClickListener(v -> {
                itemView.showContextMenu();
                return true;
            });

            expandArrow.setOnClickListener(v -> {
                isExpanded = !isExpanded;
                expandableLayout.setVisibility(isExpanded ? View.VISIBLE : View.GONE);

            });

            deleteIcon.setOnClickListener(v -> delete(v));
            editIcon.setOnClickListener(v -> edit());
        }

        private void edit(){
            Bundle bundle = new Bundle();
            bundle.putString("id", String.valueOf(relay.getId()));
            bundle.putString("on", String.valueOf(relay.getOn()));
            bundle.putString("name", tvName.getText().toString());
            bundle.putString("ip", ipDescription.getText().toString());

            Intent intent = new Intent(context, DetailsRelayActivity.class);
            intent.putExtras(bundle);

            context.startActivity(intent);
        }

        private void delete(View v){
            Snackbar snackbar = Snackbar.make(v,
                    "Do you want to delete relay: " + relay.getName(),
                    Snackbar.LENGTH_INDEFINITE
            )
                    .setDuration(5000)
                    .setAction("Yes", a -> viewModel.delete(relay.getId()))
                    .setTextColor(Color.WHITE)
                    .setActionTextColor(ContextCompat.getColor(context, R.color.deleteColor));

            snackbar.getView().setBackgroundColor(ContextCompat.getColor(context, R.color.actionDarkBackground));

            snackbar.show();
        }
    }

    public class LightViewHolder extends RecyclerView.ViewHolder{
        public LightViewHolder(@NonNull View itemView, LightViewModel viewModel) {
            super(itemView);
        }
    }
}

