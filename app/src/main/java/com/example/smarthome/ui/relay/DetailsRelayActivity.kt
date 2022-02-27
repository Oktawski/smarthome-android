package com.example.smarthome.ui.relay

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.example.smarthome.data.model.Relay
import com.example.smarthome.data.model.WifiDevice
import com.example.smarthome.databinding.DetailsRelayActivityBinding
import com.example.smarthome.ui.DeviceDetailsActivity
import com.example.smarthome.utilities.Resource
import com.example.smarthome.viewmodel.RelayViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@AndroidEntryPoint
class DetailsRelayActivity:
    DeviceDetailsActivity()
{
    override val viewModel: RelayViewModel by viewModels()
    private lateinit var device: Relay
    private lateinit var binding: DetailsRelayActivityBinding

    private var deviceId = -1L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DetailsRelayActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        deviceId = intent.getLongExtra("relayId", -1)
        getDevice()

        initLiveDataObservers()
        initOnClickListeners()
    }

    override fun initLiveDataObservers() {
        viewModel.status.observe(this){
            when (it.status) {
                Resource.Status.LOADING -> {
                   /* binding.detailsRelayFabConfirm.hide()
                    binding.detailsRelayFabCancel.hide()*/
                    binding.confirmButton.visibility = View.GONE
                    binding.cancelButton.visibility = View.GONE
                }
                Resource.Status.ADDED -> {
                    /*binding.detailsRelayFabConfirm.show()
                    binding.detailsRelayFabCancel.show()*/
                    binding.confirmButton.visibility = View.VISIBLE
                    binding.cancelButton.visibility = View.VISIBLE
                    toast(it.message)
                    finish()
                }
                Resource.Status.ERROR -> {
                    toast(it.message)
                }
                else -> toast(it.message)
            }
        }
    }

    override fun initOnClickListeners(){
        binding.confirmButton.setOnClickListener {
            viewModel.updateDevice(deviceId!!,
                Relay(binding.name.text.toString(),
                    binding.mac.text.toString(),
                    device.on))
        }
        binding.cancelButton.setOnClickListener { finish() }
    }

    override fun getDevice() {
        CoroutineScope(Dispatchers.Main).launch {
            device = viewModel.getById(deviceId!!)
            inflateViews(device)
        }
    }

    override fun inflateViews(device: WifiDevice) {
        with (binding) {
            name.setText(device.name)
            mac.setText(device.mac)
        }
    }

    private fun toast(message: String?) {
        if(message != null && message.isNotEmpty())
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
