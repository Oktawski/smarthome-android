package com.example.smarthome.user.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.Navigation
import com.example.smarthome.R
import com.example.smarthome.user.viewModels.UserViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var viewModel: UserViewModel
    private var job: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)

        val navController = Navigation.findNavController(this, R.id.nav_host_fragment_login)

        setSupportActionBar(findViewById(R.id.login_toolbar))

        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)



        viewModel.getServerStatusLD().observe(this) { bool -> run {
            if (!bool) {
                viewModel.signOut()
            }
        }}

        //startAskingForServerStatus()
        //job?.start()
    }

    private fun startAskingForServerStatus(){
        job = MainScope().launch {
            while(true){
                viewModel.getServerStatus()
                delay(1000)
            }
        }
    }
}