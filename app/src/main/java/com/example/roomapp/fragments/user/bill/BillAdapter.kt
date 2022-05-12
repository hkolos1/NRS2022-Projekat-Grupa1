package com.example.roomapp.fragments.user.bill

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.roomapp.R
import com.example.roomapp.model.Product
import com.example.roomapp.model.User
import kotlinx.android.synthetic.main.custom_row_bill.view.*

class BillAdapter: RecyclerView.Adapter<BillAdapter.MyViewHolder>() {

    private var list = emptyList<Product>()

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.custom_row_bill, parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem: Product = list[position]
        holder.itemView.prNameBill.text = currentItem.prodName
        holder.itemView.prQuanBill.text = currentItem.id.toString()
        holder.itemView.prPrice.text = currentItem.quantity.toString()
        holder.itemView.prTotal.text = currentItem.quantity.toString()

    }

    fun setData(order: List<Product>){
        this.list = order
        notifyDataSetChanged()
    }
}