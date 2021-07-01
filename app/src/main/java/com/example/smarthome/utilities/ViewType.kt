package com.example.smarthome.utilities

import androidx.annotation.LayoutRes

interface ViewType {
    @LayoutRes
    fun getViewType(): Int
}