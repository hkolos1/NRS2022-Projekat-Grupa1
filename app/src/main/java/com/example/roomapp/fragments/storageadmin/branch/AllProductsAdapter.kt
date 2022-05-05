package com.example.roomapp.fragments.storageadmin.branch

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.roomapp.R
import com.example.roomapp.model.Product
import kotlinx.android.synthetic.main.custom_row_product.view.*

class AllProductsAdapter: RecyclerView.Adapter<AllProductsAdapter.MyViewHolder>()  {

    private var productsList = emptyList<Product>()

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {}

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AllProductsAdapter.MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.custom_row_product, parent, false)
        )
    }

    override fun onBindViewHolder(holder: AllProductsAdapter.MyViewHolder, position: Int) {
        val currentItem: Product = productsList[position]

        holder.itemView.all_products_quantity.text = currentItem.quantity.toString()
        holder.itemView.all_products_name.text = currentItem.prodName
    }

    override fun getItemCount(): Int {
        return productsList.size
    }

    fun setData(products: List<Product>){
        this.productsList = products
        notifyDataSetChanged()
    }

}