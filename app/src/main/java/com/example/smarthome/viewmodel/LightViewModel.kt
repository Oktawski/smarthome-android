package com.example.smarthome.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.smarthome.data.api.LightService
import com.example.smarthome.data.model.Light
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LightViewModel @Inject constructor(
    private val service: LightService
) : ViewModel()
{
    val lights: LiveData<List<Light>> = service.lights





}