package com.example.smarthome.ui.light

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.example.smarthome.data.model.Light
import com.example.smarthome.data.model.WifiDevice
import com.example.smarthome.databinding.DetailsLightActivityBinding
import com.example.smarthome.ui.DeviceDetailsActivity
import com.example.smarthome.utilities.LiveDataObservers
import com.example.smarthome.utilities.OnClickListeners
import com.example.smarthome.utilities.Resource
import com.example.smarthome.utilities.ViewHelper
import com.example.smarthome.viewmodel.LightDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsLightActivity
    : DeviceDetailsActivity(),
    OnClickListeners,
    LiveDataObservers
{
    override val viewModel: LightDetailsViewModel by viewModels()
    private lateinit var binding: DetailsLightActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DetailsLightActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getDevice()

        initLiveDataObservers()
        initOnClickListeners()
    }

    override fun initLiveDataObservers() {
        viewModel.light.observe(this) {
            inflateViews(it)
        }

        viewModel.status.observe(this) {
            when (it.status) {
                Resource.Status.LOADING -> setLoadingLayout()
                Resource.Status.SUCCESS -> setNormalLayout()
                else -> {
                    Toast.makeText(
                        this@DetailsLightActivity,
                        it.message.orEmpty(),
                        Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }
    }

    override fun initOnClickListeners() {
        with (binding) {
            confirmButton.setOnClickListener {
                viewModel.setName(name.text.toString())
                viewModel.updateDevice()
            }
            cancelButton.setOnClickListener { finish() }
            colorWheel.setOnTouchListener(colorWheelOnTouchListener)
            gradientBar.setOnTouchListener(gradientBarOnTouchListener)
        }
    }

    override fun getDevice() =
        viewModel.getById(intent.getLongExtra("lightId", -1))

    override fun inflateViews(device: WifiDevice) {
        with (binding) {
            device as Light
            name.setText(device.name)
            mac.setText(device.mac)
            colorWheel.setRgb(device.red, device.green, device.blue)
            gradientBar.offset = (viewModel.light.value?.intensity!!).toFloat() / 255.0f
            gradientBar.endColor = colorWheel.rgb
        }
    }

    private fun setLoadingLayout() {
        with (binding) {
            ViewHelper.hideViews(confirmButton, cancelButton)
            ViewHelper.disableViews(colorWheel, gradientBar)
        }
    }

    private fun setNormalLayout() {
        with (binding) {
            ViewHelper.showViews(confirmButton, cancelButton)
            ViewHelper.enableViews(colorWheel, gradientBar)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private val colorWheelOnTouchListener = View.OnTouchListener { _, motionEvent ->
        if (motionEvent?.action == MotionEvent.ACTION_UP) {
            viewModel.setColor(binding.colorWheel.rgb)
            binding.gradientBar.endColor = binding.colorWheel.rgb
        }
        false
    }

    @SuppressLint("ClickableViewAccessibility")
    private val gradientBarOnTouchListener = View.OnTouchListener { view, motionEvent ->
        if (motionEvent?.action == MotionEvent.ACTION_UP) {
            viewModel.setIntensity((binding.gradientBar.offset * 255).toInt())
        }
        false
    }
}
