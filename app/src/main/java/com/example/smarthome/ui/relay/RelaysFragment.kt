package com.example.smarthome.ui.relay

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.smarthome.R
import com.example.smarthome.adapter.GenericRVAdapter
import com.example.smarthome.adapter.RelayViewHolder
import com.example.smarthome.data.model.Relay
import com.example.smarthome.viewmodel.RelayViewModel
import com.example.smarthome.utilities.LiveDataObservers
import com.example.smarthome.utilities.OnClickListeners
import com.google.android.material.floatingactionbutton.FloatingActionButton

class RelaysFragment:
    Fragment(R.layout.relays_fragment),
    OnClickListeners,
    LiveDataObservers{

    private lateinit var relays: List<Relay>
    private lateinit var adapter: GenericRVAdapter<Relay>
    private lateinit var recyclerViewRelays: RecyclerView
    private lateinit var fabAdd: FloatingActionButton

    private val relayViewModel: RelayViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerViewRelays = view.findViewById(R.id.relays_found_rv)
        fabAdd = view.findViewById(R.id.relays_fragment_add_fab)

        relays = mutableListOf()

        initLiveDataObservers()
        initOnClickListeners()
        initAdapter(requireActivity())
    }

    override fun initOnClickListeners(){
        fabAdd.setOnClickListener {
            startActivity(Intent(requireActivity(), AddRelayFragment::class.java)) }
    }

    override fun initLiveDataObservers() {
        relayViewModel.relays.observe(viewLifecycleOwner){
            relays = it
            adapter.update(it)
        }
    }

    private fun initAdapter(context: Context){
        adapter = object: GenericRVAdapter<Relay>(context, mutableListOf()) {
            override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
                (holder as RelayViewHolder).bind(relays[position])
                holder.itemView.tag = position
            }
        }

        recyclerViewRelays.adapter = adapter
        recyclerViewRelays.layoutManager = LinearLayoutManager(context)
    }
}