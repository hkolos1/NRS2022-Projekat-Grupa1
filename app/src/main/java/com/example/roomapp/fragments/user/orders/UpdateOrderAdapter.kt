package com.example.roomapp.fragments.user.orders

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.roomapp.R
import com.example.roomapp.model.Branch
import com.example.roomapp.model.Product
import kotlinx.android.synthetic.main.custom_row_order_product.view.*
import kotlinx.android.synthetic.main.item_product.view.prName

class UpdateOrderAdapter: RecyclerView.Adapter<UpdateOrderAdapter.MyViewHolder>() {

    private var list = emptyList<Product>()
    private var quantity = 0
    private var branchProduct= emptyList<Product>()

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
        quantity = holder.itemView.kolicina.text.toString().toInt()
/*
        holder.itemView.buttonplus.setOnClickListener{
            if ((quantity+1)<=branchProduct[position].quantity) {
                currentItem.quantity +=1
            }
        }

        holder.itemView.buttonminus.setOnClickListener{
            if ((quantity-1)>=0) {
                currentItem.quantity -=1
            }
        }*/
    }

    fun setData(order: List<Product>,branch: List<Product>){
        this.list = order
        this.branchProduct = branch
        notifyDataSetChanged()
    }
}
