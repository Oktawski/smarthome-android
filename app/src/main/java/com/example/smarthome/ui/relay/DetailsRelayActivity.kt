package com.example.smarthome.ui.relay

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.example.smarthome.data.model.Relay
import com.example.smarthome.databinding.DetailsRelayActivityBinding
import com.example.smarthome.utilities.LiveDataObservers
import com.example.smarthome.utilities.OnClickListeners
import com.example.smarthome.utilities.Resource
import com.example.smarthome.viewmodel.RelayViewModelK
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class DetailsRelayActivity:
    AppCompatActivity(),
    OnClickListeners,
    LiveDataObservers{

    private var viewModel: RelayViewModelK? = null
    private lateinit var binding: DetailsRelayActivityBinding
    private var id: Long? = null
    private lateinit var relay: Relay


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DetailsRelayActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(RelayViewModelK::class.java)
        id = intent.getLongExtra("relayId", -1)
        getRelay()

        initLiveDataObservers()
        initOnClickListeners()
    }

    override fun initLiveDataObservers() {
        viewModel?.status?.observe(this){
            when(it.status){
                Resource.Status.LOADING -> {
                    binding.detailsRelayFabConfirm.hide()
                    binding.detailsRelayFabCancel.hide()
                }
                Resource.Status.SUCCESS -> {
                    binding.detailsRelayFabConfirm.show()
                    binding.detailsRelayFabCancel.show()
                    toast(it.message)
                    finish()
                }
                Resource.Status.ERROR -> {
                    toast(it.message)
                }
            }
        }
    }

    override fun initOnClickListeners(){
        binding.detailsRelayFabConfirm.setOnClickListener{
            viewModel?.update(id!!,
                Relay(binding.detailsRelayName.text.toString(),
                    binding.detailsRelayIp.text.toString(),
                    relay.on))
        }
        binding.detailsRelayFabCancel.setOnClickListener{finish()}
    }

    private fun getRelay() {
        viewModel?.getRelayById(id!!)!!
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { inflateViews(it)
                    this.relay = it
                },
                { Toast.makeText(this, "Relay not found", Toast.LENGTH_SHORT).show() }
            )
    }

    private fun inflateViews(relay: Relay) {
        with (binding) {
            detailsRelayName.setText(relay.name)
            detailsRelayIp.setText(relay.ip)
        }
    }

    private fun toast(message: String?){
        if(message != null && message.isNotEmpty())
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}