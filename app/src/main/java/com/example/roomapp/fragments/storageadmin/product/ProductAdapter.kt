package com.example.roomapp.fragments.storageadmin.product

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.roomapp.R
import com.example.roomapp.fragments.admin.users.list.ListFragmentDirections
import com.example.roomapp.model.Product
import com.example.roomapp.model.User
import kotlinx.android.synthetic.main.custom_row.view.*
import kotlinx.android.synthetic.main.custom_row_product.view.*
import kotlinx.android.synthetic.main.item_product.view.*

class ProductAdapter: RecyclerView.Adapter<ProductAdapter.MyViewHolder>() {

    private var list = emptyList<Product>()
    private lateinit var user : User

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.custom_row_product, parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem: Product = list[position]
        holder.itemView.nameProd.text = (position+1).toString()
        holder.itemView.deliveryS.text = currentItem.prodName
        if(currentItem.round==false){
        holder.itemView.quantity.text = currentItem.quantity.toInt().toString()
        }
        else{
            holder.itemView.quantity.text = currentItem.quantity.toString()
        }
        holder.itemView.unit.text = currentItem.unit

       holder.itemView.rowLayout2.setOnClickListener{
           val action=ProductFragmentDirections.actionProductFragmentToUpdateProductFragment(currentItem,user)
           holder.itemView.findNavController().navigate(action)
        }
    }

    fun setData(product: List<Product>, user: User){
        this.list = product
        this.user = user
        notifyDataSetChanged()
    }
}
