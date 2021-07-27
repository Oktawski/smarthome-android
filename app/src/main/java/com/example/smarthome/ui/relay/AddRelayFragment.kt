package com.example.smarthome.ui.relay

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.smarthome.R
import com.example.smarthome.data.model.Relay
import com.example.smarthome.databinding.AddRelayFragmentBinding
import com.example.smarthome.utilities.LiveDataObservers
import com.example.smarthome.utilities.OnClickListeners
import com.example.smarthome.utilities.Resource
import com.example.smarthome.viewmodel.RelayViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddRelayFragment:
    Fragment(R.layout.add_relay_fragment),
    LiveDataObservers,
    OnClickListeners
{
    private var _binding: AddRelayFragmentBinding? = null
    private val binding get() = _binding!!
    private val relayViewModel: RelayViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = AddRelayFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLiveDataObservers()
        initOnClickListeners()
    }

    override fun initLiveDataObservers() {
        relayViewModel.status.observe(viewLifecycleOwner) {
            when(it.status){
                Resource.Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.addButton.visibility = View.GONE
                }
                Resource.Status.SUCCESS -> requireActivity().finish()
                else -> {
                    binding.progressBar.visibility = View.GONE
                    binding.addButton.visibility = View.VISIBLE
                }
            }
            if(!it.message.isNullOrEmpty()){
                Toast.makeText(requireActivity(), it.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun initOnClickListeners() {
        binding.addButton.setOnClickListener {
            val name: String = binding.name.text.toString()
            val ip: String = binding.ip.text.toString()
            val isOn: Boolean = binding.switchButton.isChecked

            relayViewModel.add(Relay(name, ip, isOn))
        }
    }

}
