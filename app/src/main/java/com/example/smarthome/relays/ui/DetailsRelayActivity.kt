package com.example.smarthome.relays.ui

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.example.smarthome.R
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.floatingactionbutton.FloatingActionButton

class DetailsRelayActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.details_relay_activity)


        val name = intent.getStringExtra("name")
        val ip = intent.getStringExtra("ip")

        val etName = findViewById<EditText>(R.id.details_relay_name).setText(name)
        val etIp = findViewById<EditText>(R.id.details_relay_ip).setText(ip)
        val fabConfirm = findViewById<ExtendedFloatingActionButton>(R.id.details_relay_fab_confirm)
        val fabCancel = findViewById<ExtendedFloatingActionButton>(R.id.details_relay_fab_cancel)


        fabConfirm.setOnClickListener{
            Toast.makeText(this, "Confirm clicked", Toast.LENGTH_SHORT).show()
        }

        fabCancel.setOnClickListener{finish()}
    }
}