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
        
        var vr = currentItem.time

        vr = vr.drop(4)

        vr = vr.removeRange(
            startIndex = 16,
            endIndex = 25
        )

        var finalString: String = vr.substring(
            startIndex = 0,
            endIndex = 6
        )

        finalString +=
            vr.substring(
                startIndex = 15,
                endIndex = 21
            )


        finalString = finalString.plus(" ")
        finalString = finalString.plus(
            vr.substring(
                startIndex = 7,
                endIndex = 15
            )
        )



        holder.itemView.age_txt.text = finalString

    }

    fun setData(log: List<Log>){
        this.logList = log
        notifyDataSetChanged()
    }
}