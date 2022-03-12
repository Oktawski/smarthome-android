package com.example.smarthome.ui.relay

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.smarthome.R
import com.example.smarthome.adapter.GenericRecyclerViewAdapter
import com.example.smarthome.data.model.Relay
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

    @Inject
    lateinit var adapter: GenericRecyclerViewAdapter<Relay>


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = RelaysFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLiveDataObservers()
        initOnClickListeners()
        initAdapter()
    }

    override fun onResume() {
        super.onResume()
        relayViewModel.fetchDevices()
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

    private fun initAdapter() {
        binding.relaysFoundRv.adapter = adapter
        binding.relaysFoundRv.layoutManager = LinearLayoutManager(context)
    }
}
