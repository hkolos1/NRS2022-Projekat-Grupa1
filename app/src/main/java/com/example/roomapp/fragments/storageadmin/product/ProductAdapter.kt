package com.example.roomapp.fragments.storageadmin.product

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.roomapp.R
import com.example.roomapp.model.Product

class ProductAdapter(
    private var products: List<Product>
) : RecyclerView.Adapter<ProductAdapter.ProdViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProdViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_product, parent, false)
        return ProdViewHolder(view)
    }
    override fun getItemCount(): Int = products.size
    override fun onBindViewHolder(holder: ProdViewHolder, position: Int) {
        holder.prodTitle.text = products[position].prodName;
        holder.prodDesc.text = products[position].prodDescription;
        holder.prodCode.text = products[position].prodCode;
        holder.prodAm.text = (products[position].prodAmount).toString();
    }
    fun updateProducts(prod: List<Product>) {
        this.products = prod
        notifyDataSetChanged()
    }
    inner class ProdViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val prodTitle: TextView = itemView.findViewById(R.id.prodName)
        val prodDesc: TextView = itemView.findViewById(R.id.proDes)
        val prodCode: TextView =itemView.findViewById(R.id.prodCode)
        val prodAm: TextView =itemView.findViewById(R.id.prodAmount)

    }
}
