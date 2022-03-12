package com.example.smarthome.utilities

import android.content.Context
import android.view.View
import android.widget.Toast

interface ViewHelper {

    fun hideViews(vararg view: View) {
        view.forEach { v -> v.visibility = View.GONE }
    }

    fun showViews(vararg view: View) {
        view.forEach { v -> v.visibility = View.VISIBLE }
    }

    fun disableViews(vararg view: View) {
        view.forEach { v -> v.apply {
            isEnabled = false
            isFocusable = false
        } }
    }

    fun enableViews(vararg view: View) {
        view.forEach { v -> v.apply {
            isEnabled = true
            isFocusable = true
        } }
    }

    fun toast(activity: Context, message: String?) {
        if (!message.isNullOrEmpty()) {
            Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
        }
    }
}
