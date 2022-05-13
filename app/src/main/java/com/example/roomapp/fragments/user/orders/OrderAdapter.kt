package com.example.roomapp.fragments.user.orders

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.roomapp.R
import com.example.roomapp.fragments.storageadmin.product.ProductFragmentDirections
import com.example.roomapp.model.Order
import com.example.roomapp.model.Product
import com.example.roomapp.model.User
import kotlinx.android.synthetic.main.custom_row.view.*
import kotlinx.android.synthetic.main.custom_row_bill.view.*
import kotlinx.android.synthetic.main.custom_row_order.view.*
import kotlinx.android.synthetic.main.custom_row_product.view.*
import kotlinx.android.synthetic.main.custom_row_product.view.deliveryS
import kotlinx.android.synthetic.main.custom_row_product.view.nameProd
import kotlinx.android.synthetic.main.custom_row_product.view.quantity
import kotlinx.android.synthetic.main.custom_row_product.view.unit
import kotlinx.android.synthetic.main.item_product.view.*

class OrderAdapter: RecyclerView.Adapter<OrderAdapter.MyViewHolder>() {

    private var list = emptyList<Order>()
    private lateinit var user : User

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.custom_row_order, parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem: Order = list[position]
        holder.itemView.idOrder.text = (position+1).toString()
        holder.itemView.nameOrder.text = currentItem.name
        holder.itemView.quantityProd.text = currentItem.productsQuantity.toString()
        holder.itemView.totalOrder.text = currentItem.total.toString()

        holder.itemView.rowLayoutOrder.setOnClickListener{
            val action= OrderFragmentDirections.actionOrderFragmentToUpdateOrderFragment(user,currentItem)
            holder.itemView.findNavController().navigate(action)
        }
    }

    fun setData(order: List<Order>, user: User){
        this.list = order
        this.user = user
        notifyDataSetChanged()
    }
}
