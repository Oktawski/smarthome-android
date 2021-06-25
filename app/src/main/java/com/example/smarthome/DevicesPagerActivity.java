package com.example.smarthome;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.smarthome.ligths.ui.LightsFragment;
import com.example.smarthome.relays.ui.RelaysFragment;
import com.example.smarthome.user.ui.UserActivity;
import com.example.smarthome.user.UserViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class DevicesPagerActivity extends AppCompatActivity{

    private ViewPager2 viewPager2;
    private FragmentStateAdapter adapter;
    private FloatingActionButton fabAdd;
    private Toolbar bottomToolbar;
    private UserViewModel userViewModel;
    private TabLayout tabLayout;

    private final static String[] tabs = {"Relays", "Lights"};


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.devices_pager);

        initViewModel();
        initAdapter();
        initViews();
        initToolbar();

        new TabLayoutMediator(tabLayout, viewPager2,
                (tab, position) -> {
                    tab.setText(tabs[position]);
                }).attach();

        // Current position stored in intent, sent to AddDevicePagerActivity on startActivity
        Intent intent = new Intent(this, AddDevicePagerActivity.class);
        intent.putExtra("position", viewPager2.getCurrentItem());


        // ViewPager2 OnChangeCallback puts current position in intent
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                intent.putExtra("position", viewPager2.getCurrentItem());
                super.onPageSelected(position);
            }
        });

        fabAdd.setOnClickListener(v -> startActivity(intent));
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.menu_logout:
                userViewModel.signOut();
                break;
            case R.id.menu_about:
                Toast.makeText(this, "ABOUT", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_devices:
                startActivity(new Intent(this, DevicesPagerActivity.class));
                break;
            case R.id.menu_user:
                startActivity(new Intent(this, UserActivity.class));
                break;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_toolbar_menu, menu);
        bottomToolbar.getMenu().findItem(R.id.menu_logout).setVisible(true);
        return true;
    }

    private static class DevicesPagerAdapter extends FragmentStateAdapter{
        public DevicesPagerAdapter(FragmentActivity fa){super(fa);}

        @Override
        public int getItemCount() {
            return tabs.length;
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch(position){
                case 1:
                    return new LightsFragment();
                default:
                    return new RelaysFragment();
            }
        }
    }

    private void initViewModel(){
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        userViewModel.isSignedIn().observe(this, bool -> {
            if(!bool) finish();
        });
        /*User.Companion.isSignedInLD().observe(this, bool -> {
            if(!bool) finish();
        });*/
    }

    private void initAdapter(){
        // ViewPager2 and it's adapter
        viewPager2 = findViewById(R.id.devices_pager);
        adapter = new DevicesPagerAdapter(this);
        viewPager2.setAdapter(adapter);
    }

    private void initViews(){
        fabAdd = findViewById(R.id.devices_pager_fab_add);
        tabLayout = findViewById(R.id.devices_pager_tab_layout);
    }

    private void initToolbar(){
        bottomToolbar = findViewById(R.id.main_toolbar);
        bottomToolbar.inflateMenu(R.menu.main_toolbar_menu);
        bottomToolbar.setTitle("");
        setSupportActionBar(bottomToolbar);
    }
}
