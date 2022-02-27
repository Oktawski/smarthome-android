package com.example.smarthome.utilities

import android.view.View

class ViewHelper {

    companion object {
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
    }

}