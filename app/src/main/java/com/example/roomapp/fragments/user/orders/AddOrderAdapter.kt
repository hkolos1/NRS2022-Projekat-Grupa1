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
import kotlinx.android.synthetic.main.custom_row_order_product.view.*
import kotlinx.android.synthetic.main.custom_row_product.view.*
import kotlinx.android.synthetic.main.item_product.view.*
import kotlinx.android.synthetic.main.item_product.view.prName

class AddOrderAdapter: RecyclerView.Adapter<AddOrderAdapter.MyViewHolder>() {

    private var list = emptyList<Product>()

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.custom_row_order_product, parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem: Product = list[position]
        holder.itemView.prName.text = currentItem.prodName
        holder.itemView.kolicina.text = currentItem.quantity.toString()
    }

    fun setData(order: List<Product>){
        this.list = order
        notifyDataSetChanged()
    }
}
