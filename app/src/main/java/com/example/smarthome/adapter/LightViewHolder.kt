package com.example.smarthome.adapter

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.view.ContextMenu
import android.view.Menu
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.smarthome.data.api.LightService
import com.example.smarthome.data.model.Light
import com.example.smarthome.databinding.ItemLightBinding
import com.example.smarthome.ui.light.DetailsLightActivity
import com.example.smarthome.utilities.OnClickListeners
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Dispatcher

class LightViewHolder(
    private val context: Context,
    private val service: LightService,
    private val binding: ItemLightBinding
) : RecyclerView.ViewHolder(binding.root),
    View.OnCreateContextMenuListener,
    OnClickListeners
{
    private var isExpanded = false
    private var light: Light? = null

    init {
        itemView.setOnCreateContextMenuListener(this)
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        view: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        menu?.setHeaderTitle(light!!.name)
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
            switchButton.setOnClickListener {
                CoroutineScope(Dispatchers.Main).launch {
                    service.turn(light!!.id!!)
                }
            }
            expandableView.visibility = if (isExpanded) View.VISIBLE else View.GONE

            itemView.setOnLongClickListener {
                itemView.showContextMenu()
                true
            }

            itemView.setOnClickListener { startEdit() }
            expandArrow.setOnClickListener(expandListener)
            deleteButton.setOnClickListener { showDeleteDialog() }
            editButton.setOnClickListener { startEdit() }
        }
    }

    private val expandListener = View.OnClickListener {
        isExpanded = !isExpanded
        binding.expandableView.visibility = if (isExpanded) View.VISIBLE else View.GONE
    }

    fun bind(light: Light) {
        with (binding) {
            name.text = light.name
            name.width = (binding.name.parent as View).width / 2
            switchButton.isChecked = light.on
            switchButton.isClickable = false
            macDescription.text = light.mac
            this@LightViewHolder.light = light
            initOnClickListeners()
        }
    }

    private fun startEdit() {
        val intent = Intent(context, DetailsLightActivity::class.java)
        intent.putExtra("lightId", light?.id)
        context.startActivity(intent)
    }

    private fun showDeleteDialog() {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Delete light " + light?.name)
        builder.setPositiveButton("Confirm") {
            _: DialogInterface?, _: Int ->
                CoroutineScope(Dispatchers.Main).launch {
                    service.deleteById(light?.id!!)
                    service.fetchDevices()
                }
        }
        builder.setNegativeButton("Cancel") {
            dialog: DialogInterface?, _: Int -> dialog?.dismiss()
        }
        builder.create()
        builder.show()
    }

}