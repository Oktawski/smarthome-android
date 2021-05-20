package com.example.smarthome.relays.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.smarthome.R
import com.example.smarthome.adapter.GenericRVAdapter
import com.example.smarthome.adapter.RelayViewHolder
import com.example.smarthome.relays.models.Relay
import com.example.smarthome.relays.viewModels.RelayViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class RelaysFragment: Fragment(R.layout.relays_fragment){

    private lateinit var relays: List<Relay>
    private lateinit var adapter: GenericRVAdapter<Relay>
    private lateinit var recyclerViewRelays: RecyclerView
    private lateinit var fabAdd: FloatingActionButton
    private lateinit var relayViewModel: RelayViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        relayViewModel = ViewModelProvider(requireActivity()).get(RelayViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerViewRelays = view.findViewById(R.id.relays_found_rv)
        fabAdd = view.findViewById(R.id.relays_fragment_add_fab)

        relays = mutableListOf()

        initViewModelObservables()
        initOnClickListeners()
        initAdapter(requireActivity())
    }

    private fun initOnClickListeners(){
        fabAdd.setOnClickListener {
            startActivity(Intent(requireActivity(), AddRelayFragment::class.java)) }
    }

    private fun initViewModelObservables(){
        relayViewModel.relaysLD.observe(viewLifecycleOwner){
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