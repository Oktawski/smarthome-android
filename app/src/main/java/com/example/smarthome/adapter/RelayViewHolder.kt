package com.example.smarthome.adapter

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.ContextMenu.ContextMenuInfo
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.View.OnCreateContextMenuListener
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageButton
import androidx.recyclerview.widget.RecyclerView
import com.example.smarthome.R
import com.example.smarthome.relays.models.Relay
import com.example.smarthome.relays.ui.DetailsRelayActivity
import com.example.smarthome.relays.viewModels.RelayViewModel
import com.google.android.material.switchmaterial.SwitchMaterial

class RelayViewHolder(private val context: Context,
                      itemView: View,
                      viewModel: RelayViewModel)
    : RecyclerView.ViewHolder(itemView), OnCreateContextMenuListener {

    private var isExpanded = false
    private val tvName: TextView = itemView.findViewById(R.id.name)
    private val ipDescription: TextView = itemView.findViewById(R.id.ip_description)
    private val switchMaterial: SwitchMaterial = itemView.findViewById(R.id.switch_button)
    private val expandableLayout: RelativeLayout = itemView.findViewById(R.id.expandable_view)
    private val deleteIcon: AppCompatImageButton = itemView.findViewById(R.id.delete_button)
    private val editIcon: AppCompatImageButton = itemView.findViewById(R.id.edit_button)
    private val expandArrow: AppCompatImageButton = itemView.findViewById(R.id.expand_arrow)

    private var relay: Relay? = null
    private val viewModel: RelayViewModel = viewModel

    init {
        itemView.setOnCreateContextMenuListener(this)
    }

    fun bind(relay: Relay) {
        tvName.text = relay.name
        tvName.width = (tvName.parent as View).width / 2
        switchMaterial.isChecked = relay.on
        switchMaterial.isClickable = false
        ipDescription.text = relay.ip
        this.relay = relay
        setOnClickListeners()
    }

    override fun onCreateContextMenu(menu: ContextMenu, view: View, menuInfo: ContextMenuInfo) {
        menu.setHeaderTitle(relay!!.name + " Relay")
        val edit = menu.add(Menu.FIRST, 0, Menu.NONE, "Edit")
        val delete = menu.add(Menu.FIRST, 1, Menu.NONE, "Delete")
        edit.setOnMenuItemClickListener { v: MenuItem? ->
            edit()
            true
        }
        delete.setOnMenuItemClickListener {
            showDeleteDialog()
            true
        }
    }

    private fun setOnClickListeners() {
        switchMaterial.setOnClickListener { viewModel.turn(relay!!.id) }
        expandableLayout.visibility = if (isExpanded) View.VISIBLE else View.GONE
        itemView.setOnClickListener {
            isExpanded = !isExpanded
            expandableLayout.visibility = if (isExpanded) View.VISIBLE else View.GONE
        }
        itemView.setOnLongClickListener {
            itemView.showContextMenu()
            true
        }
        expandArrow.setOnClickListener {
            isExpanded = !isExpanded
            expandableLayout.visibility = if (isExpanded) View.VISIBLE else View.GONE
        }
        deleteIcon.setOnClickListener { showDeleteDialog() }
        editIcon.setOnClickListener { edit() }
    }

    private fun edit() {
        val bundle = Bundle()
        bundle.putString("id", relay!!.id.toString())
        bundle.putString("on", relay!!.on.toString())
        bundle.putString("name", tvName.text.toString())
        bundle.putString("ip", ipDescription.text.toString())
        val intent = Intent(context, DetailsRelayActivity::class.java)
        intent.putExtras(bundle)
        context.startActivity(intent)
    }

    private fun showDeleteDialog() {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Delete relay " + relay!!.name)
        builder.setPositiveButton("Confirm") { dialog: DialogInterface?, which: Int -> viewModel.delete(relay!!.id) }
        builder.setNegativeButton("Cancel") { dialog: DialogInterface, which: Int -> dialog.dismiss() }
        builder.create()
        builder.show()
    }


}