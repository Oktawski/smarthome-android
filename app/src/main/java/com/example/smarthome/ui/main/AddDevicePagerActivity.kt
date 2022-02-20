package com.example.smarthome.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.smarthome.R
import com.example.smarthome.ui.light.AddLightFragment
import com.example.smarthome.ui.relay.AddRelayFragment
import com.example.smarthome.ui.relay.RelaysFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddDevicePagerActivity : FragmentActivity() {

    private lateinit var viewPager2: ViewPager2
    private lateinit var adapter: FragmentStateAdapter

    private val tabs = arrayOf("Light", "Relay")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_device)
        viewPager2 = findViewById(R.id.add_device_pager)
        adapter = AddDevicePagerAdapter(this)
        viewPager2.adapter = adapter

        val tabLayout: TabLayout = findViewById(R.id.add_device_tab_layout)

        TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
            tab.text = tabs[position]
        }.attach()

        val position = intent.getIntExtra("position", 0)
        viewPager2.currentItem = position
    }

    private inner class AddDevicePagerAdapter(
        fa: FragmentActivity
    ) : FragmentStateAdapter(fa) {

        override fun getItemCount(): Int = tabs.size

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> AddLightFragment()
                else -> AddRelayFragment()
            }
        }
    }
}