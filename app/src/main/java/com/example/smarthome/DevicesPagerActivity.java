package com.example.smarthome;

import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.smarthome.ligths.ui.LightsFragment;
import com.example.smarthome.relays.ui.AddRelayFragment;
import com.example.smarthome.relays.ui.RelaysFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class DevicesPagerActivity extends FragmentActivity {

    private ViewPager2 viewPager2;
    private FragmentStateAdapter adapter;

    private final static String[] tabs = {"Relays", "Lights"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.devices_pager);

        viewPager2 = findViewById(R.id.devices_pager);
        adapter = new DevicesPagerAdapter(this);
        viewPager2.setAdapter(adapter);


        TabLayout tabLayout = findViewById(R.id.devices_pager_tab_layout);

        new TabLayoutMediator(tabLayout, viewPager2,
                (tab, position) -> {
                    tab.setText(tabs[position]);
                }).attach();
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
}
