package com.example.smarthome.ui.light

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.smarthome.R
import com.example.smarthome.data.model.Light
import com.example.smarthome.databinding.AddLightFragmentBinding
import com.example.smarthome.utilities.LiveDataObservers
import com.example.smarthome.utilities.OnClickListeners
import com.example.smarthome.utilities.Resource
import com.example.smarthome.utilities.ViewHelper
import com.example.smarthome.viewmodel.LightViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddLightFragment :
    Fragment(R.layout.add_light_fragment),
    LiveDataObservers,
    OnClickListeners,
    ViewHelper
{
    private var _binding: AddLightFragmentBinding? = null
    private val binding get() = _binding!!
    private val lightViewModel: LightViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = AddLightFragmentBinding.inflate(inflater, container, false)
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
        lightViewModel.status.observe(viewLifecycleOwner) {
            when (it.status) {
                Resource.Status.LOADING -> setLoadingLayout()
                Resource.Status.ADDED -> requireActivity().finish()
                else -> setNormalLayout()
            }

            toast(requireActivity(), it.message)
        }
    }

    override fun initOnClickListeners() {
        binding.addButton.setOnClickListener {
            val name: String = binding.name.text.toString()
            val mac: String = binding.mac.text.toString()
            val isOn: Boolean = binding.switchButton.isChecked

            lightViewModel.add(Light(name, mac, isOn))
        }
    }

    private fun setLoadingLayout() {
        with (binding) {
            showViews(progressBar)
            hideViews(addButton)
        }
    }

    private fun setNormalLayout() {
        with (binding) {
            hideViews(progressBar)
            showViews(addButton)
        }
    }
}