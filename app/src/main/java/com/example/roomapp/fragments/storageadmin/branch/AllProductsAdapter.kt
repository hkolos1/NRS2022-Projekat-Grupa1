package com.example.roomapp.fragments.storageadmin.branch

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.roomapp.R
import com.example.roomapp.model.Product
import com.example.roomapp.model.User
import kotlinx.android.synthetic.main.custom_row_branch_product.view.*

class AllProductsAdapter: RecyclerView.Adapter<AllProductsAdapter.MyViewHolder>()  {

    private var productsList = emptyList<Product>()
    private lateinit var user: User

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {}

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.custom_row_branch_product, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem: Product = productsList[position]

        holder.itemView.all_products_quantity.text = currentItem.quantity.toString()
        holder.itemView.all_products_name.text = currentItem.prodName
    }

    override fun getItemCount(): Int {
        return productsList.size
    }

    fun setData(products: List<Product>, user: User){
        this.productsList = products
        this.user = user
        notifyDataSetChanged()
    }

}