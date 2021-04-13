package com.example.smarthome.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smarthome.R;
import com.example.smarthome.WifiDevice;
import com.example.smarthome.ligths.viewModels.LightViewModel;
import com.example.smarthome.relays.viewModels.RelayViewModel;

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
            case R.layout.item_relay_card_view:
                return new RelayViewHolder(context, v, new ViewModelProvider((FragmentActivity) context).get(RelayViewModel.class));
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

    public class LightViewHolder extends RecyclerView.ViewHolder{
        public LightViewHolder(@NonNull View itemView, LightViewModel viewModel) {
            super(itemView);
        }
    }
}

