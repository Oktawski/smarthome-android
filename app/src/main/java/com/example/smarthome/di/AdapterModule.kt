package com.example.smarthome.di

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.smarthome.adapter.GenericRecyclerViewAdapter
import com.example.smarthome.adapter.RelayViewHolder
import com.example.smarthome.data.api.RelayService
import com.example.smarthome.data.model.Relay
import com.example.smarthome.databinding.ItemRelayBinding
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.FragmentScoped


@Module
@InstallIn(FragmentComponent::class)
object AdapterModule {

    @Provides
    @FragmentScoped
    fun provideRelayViewHolder(
        @ActivityContext context: Context,
        service: RelayService
    ) : GenericRecyclerViewAdapter<Relay> {

        return object: GenericRecyclerViewAdapter<Relay>() {
            override fun onCreateViewHolder(
                parent: ViewGroup,
                viewType: Int
            ): RecyclerView.ViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ItemRelayBinding.inflate(inflater, parent, false)
                return RelayViewHolder(context, service, binding)
            }

            override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
                (holder as RelayViewHolder).bind(items[position])
                holder.itemView.tag = position
            }
        }

    }

}
