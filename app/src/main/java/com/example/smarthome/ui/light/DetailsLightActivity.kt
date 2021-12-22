package com.example.smarthome.ui.light

import android.graphics.Color
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
import com.example.smarthome.data.model.Light
import com.example.smarthome.data.model.WifiDevice
import com.example.smarthome.databinding.DetailsLightActivityBinding
import com.example.smarthome.ui.DeviceDetailsActivity
import com.example.smarthome.utilities.LiveDataObservers
import com.example.smarthome.utilities.OnClickListeners
import com.example.smarthome.utilities.Resource
import com.example.smarthome.viewmodel.LightViewModel
import com.github.antonpopoff.colorwheel.gradientseekbar.setBlackToColor
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailsLightActivity
    : DeviceDetailsActivity(),
    OnClickListeners,
    LiveDataObservers
{
    override val viewModel: LightViewModel by viewModels()
    private lateinit var binding: DetailsLightActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DetailsLightActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        deviceId = intent.getLongExtra("lightId", -1)
        getDevice()

        initLiveDataObservers()
        initOnClickListeners()
    }

    override fun initLiveDataObservers() {
        viewModel.status.observe(this) {
            with (binding) {
                when (it.status) {
                    Resource.Status.LOADING -> {
                        confirmButton.visibility = View.GONE
                        cancelButton.visibility = View.GONE

                        colorWheel.apply {
                            isEnabled = false
                            isFocusable = false
                        }

                        gradientBar.apply {
                            isEnabled = false
                            isFocusable = false
                        }
                    }
                    Resource.Status.SUCCESS -> {
                        confirmButton.visibility = View.VISIBLE
                        cancelButton.visibility = View.VISIBLE
                        colorWheel.apply {
                            isEnabled = true
                            isFocusable = true
                        }
                        gradientBar.apply {
                            isEnabled = true
                            isFocusable = true
                        }
                    }
                    Resource.Status.ADDED -> {
                        Toast.makeText(
                            this@DetailsLightActivity,
                            it.message.orEmpty(),
                            Toast.LENGTH_SHORT).show()
                        finish()
                    }
                    else -> Toast.makeText(
                        this@DetailsLightActivity,
                        it.message.orEmpty(),
                        Toast.LENGTH_SHORT).show()

                }
            }
        }
    }

    override fun initOnClickListeners() {
        with (binding) {
            confirmButton.setOnClickListener {
                viewModel.updateDevice(deviceId!!,
                    Light(name.text.toString(),
                        mac.text.toString(),
                        viewModel.light.value?.on!!
                    )
                )
            }

            cancelButton.setOnClickListener { finish() }

            colorWheel.setOnTouchListener(object : View.OnTouchListener {
                override fun onTouch(view: View?, motionEvent: MotionEvent?): Boolean {
                    when (motionEvent?.action) {
                        MotionEvent.ACTION_UP -> {
                            viewModel.light.value =
                                Light(null,
                                    colorWheel.rgb.red,
                                    colorWheel.rgb.green,
                                    colorWheel.rgb.blue,
                                    viewModel.light.value?.intensity!!
                                )
                            viewModel.setColor(deviceId!!)
                        }
                    }
                    return false
                }
            })

            gradientBar.setOnTouchListener(object : View.OnTouchListener {
                override fun onTouch(view: View?, motionEvent: MotionEvent?): Boolean {
                    if (motionEvent?.action == MotionEvent.ACTION_UP) {
                        viewModel.light.value?.intensity = (gradientBar.offset * 255).toInt()
                        viewModel.setColor(deviceId!!)
                    }
                    return false
                }
            })
        }
    }

    override fun getDevice() {
        CoroutineScope(Dispatchers.Main).launch {
            inflateViews(viewModel.getById(deviceId!!))
        }
    }

    override fun inflateViews(device: WifiDevice) {
        with (binding) {
            device as Light
            name.setText(device.name)
            mac.setText(device.mac)
            colorWheel.setRgb(device.red, device.green, device.blue)
            gradientBar.offset = (viewModel.light.value?.intensity!!).toFloat() / 255.0f
        }
    }
}
