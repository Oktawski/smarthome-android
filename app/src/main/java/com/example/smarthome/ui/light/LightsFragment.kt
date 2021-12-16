package com.example.smarthome.ui.light

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
import com.example.smarthome.data.model.Light
import com.example.smarthome.databinding.LightsFragmentBinding
import com.example.smarthome.utilities.LiveDataObservers
import com.example.smarthome.utilities.OnClickListeners
import com.example.smarthome.viewmodel.LightViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LightsFragment
    : Fragment(R.layout.lights_fragment),
    OnClickListeners,
    LiveDataObservers
{
    private val lightViewModel: LightViewModel by viewModels()

    private var _binding: LightsFragmentBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var adapter: GenericRecyclerViewAdapter<Light>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = LightsFragmentBinding.inflate(inflater, container, false)
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
        lightViewModel.fetch()
    }

    override fun initOnClickListeners() {
        binding.addButton.setOnClickListener {
            startActivity(Intent(requireActivity(), AddLightFragment::class.java))
        }
    }

    override fun initLiveDataObservers() {
        lightViewModel.lights.observe(viewLifecycleOwner) {
            adapter.update(it)
        }
    }

    private fun initAdapter() {
        binding.lightsFoundRv.adapter = adapter
        binding.lightsFoundRv.layoutManager = LinearLayoutManager(context)
    }
}
