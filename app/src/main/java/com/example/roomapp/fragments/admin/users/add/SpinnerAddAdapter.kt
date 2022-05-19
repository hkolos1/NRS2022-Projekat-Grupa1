package com.example.roomapp.fragments.admin.users.add

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class SpinnerAddAdapter(context: Context, objects: List<String>):
    ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, objects) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val itemView = super.getView(position, convertView, parent) as TextView
        itemView.text = getItem(position).toString()
        return itemView
    }
}