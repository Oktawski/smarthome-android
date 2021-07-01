package com.example.smarthome.ui.user

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.smarthome.R

class UserActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)

        setContentView(R.layout.user_details_fragment)
    }
}