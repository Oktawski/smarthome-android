package com.example.smarthome;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.smarthome.user.models.User;
import com.example.smarthome.user.ui.LoginActivity;
import com.google.android.material.appbar.MaterialToolbar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);


        if(!User.Companion.isSignedIn()) {
            startActivity(new Intent(this, LoginActivity.class));
        }
    }
}
