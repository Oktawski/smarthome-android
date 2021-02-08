package com.example.smarthome.adapter

import androidx.annotation.LayoutRes

interface ViewType {
    @LayoutRes
    fun getViewType(): Int
}