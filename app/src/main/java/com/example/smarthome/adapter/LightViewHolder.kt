package com.example.smarthome.adapter

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.view.ContextMenu
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.smarthome.data.api.LightService
import com.example.smarthome.data.model.Light
import com.example.smarthome.databinding.ItemLightBinding
import com.example.smarthome.ui.light.DetailsLightActivity
import com.example.smarthome.utilities.OnClickListeners

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
        TODO("Not yet implemented")
    }

    override fun initOnClickListeners() {
        with (binding) {
            switchButton.setOnClickListener { service.turn(light!!.id!!) }
            expandableView.visibility = if (isExpanded) View.VISIBLE else View.GONE

            itemView.setOnLongClickListener {
                itemView.showContextMenu()
                true
            }

            itemView.setOnClickListener(expandListener)
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
            _: DialogInterface?, _: Int -> service.deleteById(light?.id!!)
        }
        builder.setNegativeButton("Cancel") {
            dialog: DialogInterface?, _: Int -> dialog?.dismiss()
        }
        builder.create()
        builder.show()
    }

}