package com.example.smarthome.adapter

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.ContextMenu.ContextMenuInfo
import android.view.Menu
import android.view.View
import android.view.View.OnCreateContextMenuListener
import androidx.recyclerview.widget.RecyclerView
import com.example.smarthome.data.model.Relay
import com.example.smarthome.databinding.ItemRelayBinding
import com.example.smarthome.ui.relay.DetailsRelayActivity
import com.example.smarthome.viewmodel.RelayViewModel

class RelayViewHolder(private val context: Context,
                      private val binding: ItemRelayBinding,
                      private val itemView: View,
                      var viewModel: RelayViewModel
) : RecyclerView.ViewHolder(itemView), OnCreateContextMenuListener {

    private var isExpanded = false
    private var relay: Relay? = null

    init {
        itemView.setOnCreateContextMenuListener(this)
    }

    fun bind(relay: Relay) {

        binding.name.text = relay.name
        binding.name.width = (binding.name.parent as View).width / 2
        binding.switchButton.isChecked = relay.on
        binding.switchButton.isClickable = false
        binding.ipDescription.text = relay.ip
        this.relay = relay
        setOnClickListeners()
    }

    override fun onCreateContextMenu(menu: ContextMenu?, view: View?, menuInfo: ContextMenuInfo?) {
        menu?.setHeaderTitle(relay!!.name + " Relay")
        val edit = menu?.add(Menu.FIRST, 0, Menu.NONE, "Edit")
        val delete = menu?.add(Menu.FIRST, 1, Menu.NONE, "Delete")
        edit?.setOnMenuItemClickListener {
            edit()
            true
        }
        delete?.setOnMenuItemClickListener {
            showDeleteDialog()
            true
        }
    }

    private fun setOnClickListeners() {
        with(binding){
            switchButton.setOnClickListener { viewModel.turn(relay!!.id) }
            expandableView.visibility = if (isExpanded) View.VISIBLE else View.GONE
            itemView.setOnClickListener {
                isExpanded = !isExpanded
                expandableView.visibility = if (isExpanded) View.VISIBLE else View.GONE
            }
            itemView.setOnLongClickListener {
                itemView.showContextMenu()
                true
            }
            expandArrow.setOnClickListener {
                isExpanded = !isExpanded
                expandableView.visibility = if (isExpanded) View.VISIBLE else View.GONE
            }
            deleteButton.setOnClickListener { showDeleteDialog() }
            editButton.setOnClickListener { edit() }
        }
    }

    private fun edit() {
        val bundle = Bundle()
        bundle.putString("id", relay?.id.toString())
        bundle.putString("on", relay?.on.toString())
        bundle.putString("name", binding.name.text.toString())
        bundle.putString("ip", binding.ipDescription.text.toString())
        val intent = Intent(context, DetailsRelayActivity::class.java)
        intent.putExtras(bundle)
        context.startActivity(intent)
    }

    private fun showDeleteDialog() {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Delete relay " + relay?.name)
        builder.setPositiveButton("Confirm") { _: DialogInterface?, _: Int -> viewModel.delete(relay?.id) }
        builder.setNegativeButton("Cancel") { dialog: DialogInterface?, _: Int -> dialog?.dismiss() }
        builder.create()
        builder.show()
    }
}