package com.example.smarthome.di

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.example.smarthome.adapter.GenericRecyclerViewAdapter
import com.example.smarthome.adapter.LightViewHolder
import com.example.smarthome.adapter.RelayViewHolder
import com.example.smarthome.data.api.LightService
import com.example.smarthome.data.api.RelayService
import com.example.smarthome.data.model.Light
import com.example.smarthome.data.model.Relay
import com.example.smarthome.databinding.ItemLightBinding
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
            override var differ = AsyncListDiffer(this, differCallback)

            override fun onCreateViewHolder(
                parent: ViewGroup,
                viewType: Int
            ): RecyclerView.ViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ItemRelayBinding.inflate(inflater, parent, false)
                return RelayViewHolder(context, service, binding)
            }

            override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
                (holder as RelayViewHolder).bind(differ.currentList[position])
            }

            override fun getItemCount(): Int {
                return differ.currentList.size
            }
        }
    }

    @Provides
    @FragmentScoped
    fun provideLightViewHolder(
        @ActivityContext context: Context,
        service: LightService
    ) : GenericRecyclerViewAdapter<Light> {
        return object : GenericRecyclerViewAdapter<Light>() {
            override var differ = AsyncListDiffer(this, differCallback)

            override fun onCreateViewHolder(
                parent: ViewGroup,
                viewType: Int
            ): RecyclerView.ViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ItemLightBinding.inflate(inflater, parent, false)
                return LightViewHolder(context, service, binding)
            }

            override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
                (holder as LightViewHolder).bind(differ.currentList[position])
            }

            override fun getItemCount(): Int {
                return differ.currentList.size
            }
        }
    }

}
