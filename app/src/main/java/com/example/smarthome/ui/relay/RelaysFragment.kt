package com.example.smarthome.ui.relay

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.smarthome.R
import com.example.smarthome.adapter.GenericRecyclerViewAdapter
import com.example.smarthome.adapter.RelayViewHolder
import com.example.smarthome.data.model.Relay
import com.example.smarthome.databinding.ItemRelayBinding
import com.example.smarthome.databinding.RelaysFragmentBinding
import com.example.smarthome.utilities.LiveDataObservers
import com.example.smarthome.utilities.OnClickListeners
import com.example.smarthome.viewmodel.RelayViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RelaysFragment
    : Fragment(R.layout.relays_fragment),
    OnClickListeners,
    LiveDataObservers
{
    private val relayViewModel: RelayViewModel by viewModels()

    private var _binding: RelaysFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: GenericRecyclerViewAdapter<Relay>


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = RelaysFragmentBinding.inflate(inflater, container, false)
        relayViewModel.fetch()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLiveDataObservers()
        initOnClickListeners()
        initAdapter(requireActivity())
    }

    override fun onResume() {
        super.onResume()
        relayViewModel.fetch()
    }

    override fun initOnClickListeners(){
        binding.addButton.setOnClickListener {
            startActivity(Intent(requireActivity(), AddRelayFragment::class.java)) }
    }

    override fun initLiveDataObservers() {
        relayViewModel.relays.observe(viewLifecycleOwner) {
            adapter.update(it)
        }
    }

    private fun initAdapter(context: Context) {
        adapter = object: GenericRecyclerViewAdapter<Relay>(
            mutableListOf(),
            relayViewModel
        ) {
            override fun onCreateViewHolder(
                parent: ViewGroup,
                viewType: Int
            ): RecyclerView.ViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ItemRelayBinding.inflate(inflater, parent, false)
                return RelayViewHolder(context, binding, relayViewModel)
            }

            override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
                (holder as RelayViewHolder).bind(items[position])
                holder.itemView.tag = position
            }
        }

        binding.relaysFoundRv.adapter = adapter
        binding.relaysFoundRv.layoutManager = LinearLayoutManager(context)
    }

}
