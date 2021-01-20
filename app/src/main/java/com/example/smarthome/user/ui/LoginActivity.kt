package com.example.smarthome.user.ui

import android.os.Bundle
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import com.example.smarthome.R

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)

        val navController = Navigation.findNavController(this, R.id.nav_host_fragment_login)

        setSupportActionBar(findViewById(R.id.login_toolbar))
    }
}