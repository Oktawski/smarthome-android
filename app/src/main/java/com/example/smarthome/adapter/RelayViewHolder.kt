package com.example.smarthome.adapter

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.view.ContextMenu
import android.view.ContextMenu.ContextMenuInfo
import android.view.Menu
import android.view.View
import android.view.View.OnCreateContextMenuListener
import androidx.recyclerview.widget.RecyclerView
import com.example.smarthome.data.api.RelayService
import com.example.smarthome.data.model.Relay
import com.example.smarthome.databinding.ItemRelayBinding
import com.example.smarthome.ui.relay.DetailsRelayActivity
import com.example.smarthome.utilities.OnClickListeners


class RelayViewHolder (
    private val context: Context,
    private val relayService: RelayService,
    private val binding: ItemRelayBinding
) : RecyclerView.ViewHolder(binding.root),
    OnCreateContextMenuListener,
    OnClickListeners
{
    private var isExpanded = false
    private var relay: Relay? = null

    init {
        itemView.setOnCreateContextMenuListener(this)
    }

    override fun onCreateContextMenu(menu: ContextMenu?, view: View?, menuInfo: ContextMenuInfo?) {
        menu?.setHeaderTitle(relay!!.name + " Relay")
        val edit = menu?.add(Menu.FIRST, 0, Menu.NONE, "Edit")
        val delete = menu?.add(Menu.FIRST, 1, Menu.NONE, "Delete")
        edit?.setOnMenuItemClickListener {
            startEdit()
            true
        }
        delete?.setOnMenuItemClickListener {
            showDeleteDialog()
            true
        }
    }

    override fun initOnClickListeners() {
        with (binding) {
            switchButton.setOnClickListener { relayService.turn(relay!!.id!!) }
            expandableView.visibility = if (isExpanded) View.VISIBLE else View.GONE

            itemView.setOnLongClickListener {
                itemView.showContextMenu()
                true
            }

            itemView.setOnClickListener(expandListener)
            expandArrow.setOnClickListener(expandListener)
            deleteButton.setOnClickListener { showDeleteDialog() }
            editButton.setOnClickListener { startEdit() }
        }
    }

    private val expandListener = View.OnClickListener {
        isExpanded = !isExpanded
        binding.expandableView.visibility = if (isExpanded) View.VISIBLE else View.GONE
    }

    fun bind(relay: Relay) {
        with (binding) {
            name.text = relay.name
            name.width = (binding.name.parent as View).width / 2
            switchButton.isChecked = relay.on
            switchButton.isClickable = false
            ipDescription.text = relay.ip
            this@RelayViewHolder.relay = relay
            initOnClickListeners()
        }
    }

    private fun startEdit() {
        val intent = Intent(context, DetailsRelayActivity::class.java)
        intent.putExtra("relayId", relay?.id)
        context.startActivity(intent)
    }

    private fun showDeleteDialog() {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Delete relay " + relay?.name)
        builder.setPositiveButton("Confirm") {
                _: DialogInterface?, _: Int -> relayService.deleteById(relay?.id!!) }
        builder.setNegativeButton("Cancel") {
                dialog: DialogInterface?, _: Int -> dialog?.dismiss() }
        builder.create()
        builder.show()
    }
}