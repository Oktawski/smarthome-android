package com.example.smarthome;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.smarthome.ligths.ui.AddLightFragment;
import com.example.smarthome.relays.ui.AddRelayFragment;
import com.example.smarthome.relays.ui.RelaysFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class AddDevicePagerActivity extends FragmentActivity {

    private ViewPager2 viewPager;
    private FragmentStateAdapter adapter;

    private final static String[] tabs = {"Relay", "Light"};


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_device_pager);

        // ViewPager and it's adapter
        viewPager = findViewById(R.id.add_device_pager);
        adapter = new AddDevicePagerAdapter(this);
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = findViewById(R.id.add_device_tab_layout);

        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> {
                    tab.setText(tabs[position]);
                }).attach();

        // Position acquired from DevicesPagerActivity, viewPager currentPosition set
        // accordingly
        int position = getIntent().getIntExtra("position", 0);
        viewPager.setCurrentItem(position);
    }


    private static class AddDevicePagerAdapter extends FragmentStateAdapter {
        public AddDevicePagerAdapter(FragmentActivity fa){
            super(fa);
        }

        @Override
        public int getItemCount() {
            return 2;
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch(position){
                case 0:
                    return new AddRelayFragment();
                case 1:
                    return new AddLightFragment();
                default:
                    return new RelaysFragment();
            }
        }
    }


}
