package com.example.roomapp.fragments.admin.log

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.roomapp.R
import com.example.roomapp.model.Log
import kotlinx.android.synthetic.main.custom_row.view.*

class LoggingAdapter: RecyclerView.Adapter<LoggingAdapter.MyViewHolder>() {

    private var logList = emptyList<Log>()

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.custom_row_log, parent, false))
    }

    override fun getItemCount(): Int {
       return logList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem: Log = logList[position]
        holder.itemView.firstName_txt.text = currentItem.userName
        holder.itemView.lastName_txt.text = currentItem.action
        holder.itemView.age_txt.text = currentItem.time

    }

    fun setData(log: List<Log>){
        this.logList = log
        notifyDataSetChanged()
    }
}