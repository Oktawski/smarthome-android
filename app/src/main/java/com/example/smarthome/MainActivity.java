package com.example.smarthome;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.smarthome.user.models.User;
import com.example.smarthome.user.viewModels.UserViewModel;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private UserViewModel model;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        model = new ViewModelProvider(this).get(UserViewModel.class);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        toolbar = findViewById(R.id.main_toolbar);
        toolbar.setTitle("");
        toolbar.inflateMenu(R.menu.main_toolbar_menu);

        setSupportActionBar(toolbar);

        model.getIsSignedIn().observe(this, bool -> {
            if(!bool){
                finish();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_toolbar_menu, menu);
        toolbar.getMenu().findItem(R.id.menu_logout).setVisible(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.menu_logout:
                model.signOut();
                break;
            case R.id.menu_about:
                Log.i("TAG", "onOptionsItemSelected: ABOUT");
                break;
            case R.id.menu_devices:
                startActivity(new Intent(this, DevicesPagerActivity.class));
        }
        return true;
    }
}
