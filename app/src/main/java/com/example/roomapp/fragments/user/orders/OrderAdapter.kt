package com.example.roomapp.fragments.user.orders

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.roomapp.R
import com.example.roomapp.model.Order
import com.example.roomapp.model.Product
import com.example.roomapp.model.User
import kotlinx.android.synthetic.main.custom_row.view.*
import kotlinx.android.synthetic.main.custom_row_bill.view.*
import kotlinx.android.synthetic.main.custom_row_product.view.*
import kotlinx.android.synthetic.main.item_product.view.*

class OrderAdapter: RecyclerView.Adapter<OrderAdapter.MyViewHolder>() {

    private var list = emptyList<Order>()
    private lateinit var user : User

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.custom_row_product, parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem: Order = list[position]
        holder.itemView.nameProd.text = (position+1).toString()
        holder.itemView.deliveryS.text = currentItem.userId.toString()
        holder.itemView.quantity.text = currentItem.time
        holder.itemView.unit.text = currentItem.total.toString()


    }

    fun setData(order: List<Order>, user: User){
        this.list = order
        this.user = user
        notifyDataSetChanged()
    }
}
