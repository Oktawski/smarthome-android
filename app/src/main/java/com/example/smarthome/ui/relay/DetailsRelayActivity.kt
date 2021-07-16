package com.example.smarthome.ui.relay

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
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
    LiveDataObservers
{
    private val viewModel: RelayViewModelK by viewModels()
    private var id: Long? = null
    private lateinit var relay: Relay
    private lateinit var binding: DetailsRelayActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DetailsRelayActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //viewModel = ViewModelProvider(this).get(RelayViewModelK::class.java)
        id = intent.getLongExtra("relayId", -1)
        getRelay()

        initLiveDataObservers()
        initOnClickListeners()
    }

    override fun initLiveDataObservers() {
        viewModel.status.observe(this){
            when(it.status){
                Resource.Status.LOADING -> {
                   /* binding.detailsRelayFabConfirm.hide()
                    binding.detailsRelayFabCancel.hide()*/
                    binding.confirmButton.visibility = View.GONE
                    binding.cancelButton.visibility = View.GONE
                }
                Resource.Status.SUCCESS -> {
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
            viewModel?.update(id!!,
                Relay(binding.name.text.toString(),
                    binding.ip.text.toString(),
                    relay.on))
        }
        binding.cancelButton.setOnClickListener { finish() }
    }

    private fun getRelay() {
        viewModel?.getById(id!!)!!
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
            name.setText(relay.name)
            ip.setText(relay.ip)
        }
    }

    private fun toast(message: String?) {
        if(message != null && message.isNotEmpty())
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}