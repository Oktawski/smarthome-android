package com.example.smarthome.relays.ui

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.smarthome.R
import com.example.smarthome.relays.models.Relay
import com.example.smarthome.relays.viewModels.RelayViewModel
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.floatingactionbutton.FloatingActionButton

class DetailsRelayActivity : AppCompatActivity() {

    private var viewModel: RelayViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.details_relay_activity)

        viewModel = ViewModelProvider(this).get(RelayViewModel::class.java)

        val id: Long =  intent.getStringExtra("id")!!.toLong()
        val on: Boolean = intent.getStringExtra("on")!!.toBoolean()
        val name = intent.getStringExtra("name")
        val ip = intent.getStringExtra("ip")

        val etName = findViewById<EditText>(R.id.details_relay_name)
        val etIp = findViewById<EditText>(R.id.details_relay_ip)
        val fabConfirm = findViewById<ExtendedFloatingActionButton>(R.id.details_relay_fab_confirm)
        val fabCancel = findViewById<ExtendedFloatingActionButton>(R.id.details_relay_fab_cancel)

        etName.setText(name)
        etIp.setText(ip)


        viewModel!!.updateStatus.observe(this, Observer { bool -> run{
            if(bool){
                Toast.makeText(this, "Relay updated", Toast.LENGTH_LONG).show()
                finish()
            }
        } })


        fabConfirm.setOnClickListener{
            fabConfirm.hide()
            fabCancel.hide()
            viewModel!!.update(id, Relay(etName.text.toString(), etIp.text.toString(), on))
        }

        fabCancel.setOnClickListener{finish()}
    }
}