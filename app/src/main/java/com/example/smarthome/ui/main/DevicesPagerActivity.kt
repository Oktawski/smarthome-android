package com.example.smarthome.ui.main

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.smarthome.R
import com.example.smarthome.databinding.ActivityDevicesBinding
import com.example.smarthome.ui.light.LightsFragment
import com.example.smarthome.ui.relay.RelaysFragment
import com.example.smarthome.ui.user.UserActivity
import com.example.smarthome.utilities.LiveDataObservers
import com.example.smarthome.utilities.OnClickListeners
import com.example.smarthome.viewmodel.UserViewModel
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DevicesPagerActivity
    : AppCompatActivity(),
    LiveDataObservers,
    OnClickListeners {

    private val userViewModel: UserViewModel by viewModels()
    private lateinit var binding: ActivityDevicesBinding

    private val tabs = arrayOf("Relays", "Lights")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDevicesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initLiveDataObservers()
        initAdapter()
        initToolbar()

        with (binding) {
            TabLayoutMediator(tabLayout, devicesPager) { tab, position ->
                tab.text = tabs[position]
            }.attach()

            val addDeviceIntent = Intent(
                this@DevicesPagerActivity,
                AddDevicePagerActivity::class.java)

            addDeviceIntent.putExtra("position", devicesPager.currentItem)

            devicesPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    addDeviceIntent.putExtra("position", devicesPager.currentItem)
                    super.onPageSelected(position)
                }
            })

            addButton.setOnClickListener { startActivity(addDeviceIntent) }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_logout -> userViewModel.signOut()
            R.id.menu_about -> Toast.makeText(this, "About", Toast.LENGTH_SHORT).show()
            R.id.menu_user -> startActivity(Intent(this, UserActivity::class.java))
        }
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_toolbar_menu, menu)
        binding.mainToolbar.menu.findItem(R.id.menu_logout).isVisible = true
        return true
    }

    override fun initLiveDataObservers() {
        userViewModel.isSignedIn.observe(this) { if(!it) finish() }
    }

    override fun initOnClickListeners() {
        //binding.addButton.setOnClickListener { startActivity(addDeviceIntent) }
    }

    private fun initToolbar() {
        with (binding) {
            mainToolbar.inflateMenu(R.menu.main_toolbar_menu)
            mainToolbar.title = ""
            setSupportActionBar(mainToolbar)
        }
    }

    private fun initAdapter() {
        with (binding) {
            val adapter = DevicesPagerAdapter(this@DevicesPagerActivity)
            devicesPager.adapter = adapter
        }
    }

    private inner class DevicesPagerAdapter(fa: FragmentActivity)
        : FragmentStateAdapter(fa) {

        override fun getItemCount(): Int = tabs.size

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> RelaysFragment()
                1 -> LightsFragment()
                else -> RelaysFragment()
            }
        }
    }
}