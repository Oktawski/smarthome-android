package com.example.smarthome.ui.user

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.observe
import androidx.navigation.Navigation
import com.example.smarthome.R
import com.example.smarthome.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private var job: Job? = null

    private val viewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)

        val navController = Navigation.findNavController(this, R.id.nav_host_fragment_login)

        setSupportActionBar(findViewById(R.id.login_toolbar))

        viewModel.serverStatus.observe(this) { bool -> run {
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