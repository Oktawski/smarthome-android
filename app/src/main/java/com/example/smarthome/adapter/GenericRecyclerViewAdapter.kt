package com.example.smarthome.adapter

import android.content.Context
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.example.smarthome.WifiDevice


abstract class GenericRecyclerViewAdapter<T: WifiDevice>(
    private val context: Context,
    val items: MutableList<T>,
    private val viewModel: ViewModel
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        throw UnsupportedOperationException()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int){
        throw UnsupportedOperationException()
    }

    override fun getItemCount(): Int = items.size

    fun update(items: List<T>){
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }
}